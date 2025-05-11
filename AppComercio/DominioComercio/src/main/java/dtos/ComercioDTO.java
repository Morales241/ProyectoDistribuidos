package dtos;

public class ComercioDTO {

    private String nombre;
    private String tipo;

    public ComercioDTO() {}

    public ComercioDTO(String nombre, String correo, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
