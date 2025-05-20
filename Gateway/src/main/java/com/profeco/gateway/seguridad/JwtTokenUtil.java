package com.profeco.gateway.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final int MINUTOS_VALIDO = 30;
    private final String claveSecreta = "dhntmnujnmrbhewrfvrrtuj57hbyb57uj4rfcwtg651evnoioento";

    public String generateToken(String id) {
        Claims claims = Jwts.claims().subject(id).build();
        long tiempoMilis = System.currentTimeMillis();
        long expiracion = tiempoMilis + MINUTOS_VALIDO * 1000 * 60;
        Date exp = new Date(expiracion);
        return Jwts.builder().claims(claims).issuedAt(new Date(tiempoMilis)).expiration(exp)
                .signWith(SignatureAlgorithm.HS256, claveSecreta).compact();
    }

    public boolean validarToken(final String header) {
        String[] parts = header.split(" ");
        if (parts.length != 2 || !"Bearer".equals(parts[0])) {
            return false;
        }

        Jwts.parser().setSigningKey(claveSecreta).build().parseClaimsJws(parts[1]);
        return true;
    }
}