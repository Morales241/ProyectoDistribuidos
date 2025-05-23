package com.profeco.gateway.seguridad;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class ValidadorRouter {
    public static final List<String> enpointsAbiertos = List.of(
            "/registrar",
            "/eureka",
            "/inicioSesion",
            "/generarToken",
            "/CrerCarrito",
            "/consumidoresComercio",
            "/multas",
            "/reportesProfeco",
            "/reviciones"
    );

    public Predicate<ServerHttpRequest> esSeguro =
            request -> enpointsAbiertos
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}