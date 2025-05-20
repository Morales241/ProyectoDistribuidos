package com.profeco.jwt.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("DOMINICOCONSUMIDOR")
public interface FeignConsumidor {
    @RequestMapping("/consumidores/inicioSesion")
    ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String contrasena);
}
