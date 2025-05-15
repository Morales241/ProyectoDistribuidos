package com.profeco.gateway.dtos;

import lombok.Data;

@Data
public class JwtPeticion {
    private String usuario;

    private String contrasena;
}
