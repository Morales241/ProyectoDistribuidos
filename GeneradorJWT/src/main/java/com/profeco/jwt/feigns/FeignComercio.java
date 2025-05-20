package com.profeco.jwt.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("DOMINIOCOMERCIO")
public interface FeignComercio {
    @PostMapping("/comercios/inicioSesion")
    ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String contrasena);
}
