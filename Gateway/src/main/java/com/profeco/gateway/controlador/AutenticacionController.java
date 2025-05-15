package com.profeco.gateway.controlador;

import com.profeco.gateway.dtos.JwtPeticion;
import com.profeco.gateway.seguridad.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/gateway")
public class AutenticacionController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/crearToken")
    public ResponseEntity<String> crearToken(@RequestBody JwtPeticion peticion) {
        if (!autenticar(peticion.getUsuario(), peticion.getContrasena())) {
            return ResponseEntity.notFound().build();
        }

        String token = jwtTokenUtil.generateToken(peticion.getUsuario());
        return ResponseEntity.ok(token);
    }

    private boolean autenticar(String usuario, String contrasena) {
        return usuario.equals("pepe") && contrasena.equals("pass");
    }
}
