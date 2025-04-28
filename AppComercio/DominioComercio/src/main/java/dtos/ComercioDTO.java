package dtos;

public class ComercioDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String tipo;
    private String contrasena;

    public ComercioDTO() {}

    public ComercioDTO(String nombre, String correo, String tipo) {
        this.nombre = nombre;
        this.correo = correo;
        this.tipo = tipo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsurio() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
