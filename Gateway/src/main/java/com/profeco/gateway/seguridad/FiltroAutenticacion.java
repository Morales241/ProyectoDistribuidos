package com.profeco.gateway.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FiltroAutenticacion extends AbstractGatewayFilterFactory<FiltroAutenticacion.Config> {

    @Autowired
    private final ValidadorRouter validadorRouter;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public FiltroAutenticacion(ValidadorRouter validadorRouter, JwtTokenUtil jwtTokenUtil) {
        super(Config.class);
        this.validadorRouter = validadorRouter;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validadorRouter.esSeguro.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorisation Header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();

                if (!jwtTokenUtil.validarToken(authHeader)) {
                    throw new RuntimeException("Autorizacion no valida");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
