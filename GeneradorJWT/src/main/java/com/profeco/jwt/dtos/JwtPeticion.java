package com.profeco.jwt.dtos;

import lombok.Data;

@Data
public class JwtPeticion {

    private String tipoUsuario;

    private String correo;

    private String contrasena;
}
