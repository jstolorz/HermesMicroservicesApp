package com.bluesoft.hermesapigateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) ->{
            final ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey("Authorization")){
                return onError(exchange,"No authorization header", HttpStatus.UNAUTHORIZED);
            }

            final String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            final String jwt = authorizationHeader.replace("Bearer","");

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(final ServerWebExchange exchange, final String no_authorization_header, final HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        return response.setComplete();
    }

    public static class Config{ }
}
