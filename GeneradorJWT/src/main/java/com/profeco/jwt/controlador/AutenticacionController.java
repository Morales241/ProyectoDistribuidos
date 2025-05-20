package com.profeco.jwt.controlador;

import com.profeco.jwt.dtos.JwtPeticion;
import com.profeco.jwt.feigns.FeignComercio;
import com.profeco.jwt.feigns.FeignConsumidor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {
    private final String claveSecreta = "secreto";

    @Autowired
    private FeignComercio clienteComercio;

    @Autowired
    private FeignConsumidor clienteConsumidor;

    @PostMapping("/generarToken")
    public ResponseEntity<String> crearToken(@RequestBody JwtPeticion peticion) {
        if (!autenticar(peticion.getCorreo(), peticion.getContrasena(), peticion.getTipoUsuario())) {
            return ResponseEntity.notFound().build();
        }

        String token = generarToken(peticion.getCorreo());
        return ResponseEntity.ok(token);
    }

    private boolean autenticar(String correo, String contrasena, String tipoUsuario) {
        Object usuario = null;
        if (tipoUsuario.equals("consumidor")) {
            usuario = clienteConsumidor.inicioSesion(correo, contrasena);
        } else if (tipoUsuario.equals("comercio")) {
            usuario = clienteComercio.inicioSesion(correo, contrasena);
        }

        return usuario!=null;
    }

    private String generarToken(String id) {
        Claims claims = Jwts.claims().subject(id).build();
        long tiempoMilis = System.currentTimeMillis();
        int MINUTOS_VALIDO = 30;
        long expiracion = tiempoMilis + MINUTOS_VALIDO * 1000 * 60;
        Date exp = new Date(expiracion);
        return Jwts.builder().claims(claims).issuedAt(new Date(tiempoMilis)).expiration(exp)
                .signWith(SignatureAlgorithm.HS256, claveSecreta).compact();
    }
}
