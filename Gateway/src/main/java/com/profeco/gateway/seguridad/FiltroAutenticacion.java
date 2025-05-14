package com.profeco.gateway.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

public class FiltroAutenticacion extends AbstractGatewayFilterFactory<FiltroAutenticacion.Config> {

    @Autowired
    private final ValidadorRouter routerValidator;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public FiltroAutenticacion(ValidadorRouter routerValidator, JwtTokenUtil jwtTokenUtil) {
        super(Config.class);
        this.routerValidator = routerValidator;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routerValidator.esSeguro.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorisation Header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();

                if (!jwtTokenUtil.validarToken(authHeader)) {
                    throw new RuntimeException("");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
