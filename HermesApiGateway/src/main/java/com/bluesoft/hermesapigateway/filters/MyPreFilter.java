package com.bluesoft.hermesapigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class MyPreFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(MyPreFilter.class);

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        logger.info("My first pre filter is executed...");

        final String requestPath = exchange.getRequest().getPath().toString();
        logger.info("Request path = " + requestPath);

        final HttpHeaders headers = exchange.getRequest().getHeaders();
        final Set<String> headersNames = headers.keySet();

        headersNames.forEach((headerName) -> {
            final String headersValue = headers.getFirst(headerName);
            logger.info(headerName + " " + headersValue);
        });

        return chain.filter(exchange);
    }
}
