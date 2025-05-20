package com.profeco.jwt.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("DOMINIOCONSUMIDOR")
public interface FeignConsumidor {
    @PostMapping("/consumidores/inicioSesion")
    ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String contrasena);
}
