package dtos;


import java.time.LocalDate;

public class ConsumidorDTO {
    private String nombre;

    private String correo;


    public ConsumidorDTO() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
