package com.bluesoft.hermesapigateway.filters;

import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final Environment environment;

    public AuthorizationHeaderFilter(final Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    public static class Config{ }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) ->{
            final ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey("Authorization")){
                return onError(exchange,"No authorization header", HttpStatus.UNAUTHORIZED);
            }

            final String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            final String jwt = authorizationHeader.replace("Bearer","");

            if(!isJwtValid(jwt)){
                return onError(exchange,"JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(final ServerWebExchange exchange, final String no_authorization_header, final HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        return response.setComplete();
    }

    private boolean isJwtValid(final String jwt){
        boolean returnValue = true;

        String signingKeyB64= Base64.getEncoder().encodeToString("signingKey".getBytes(StandardCharsets.UTF_8));

        String subject = null;

        try {
            subject = Jwts.parser()
                    .setSigningKey(signingKeyB64)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }catch (Exception ex){
            returnValue = false;
        }

        if(subject == null || subject.isEmpty()){
            returnValue = false;
        }

        return returnValue;
    }
}
