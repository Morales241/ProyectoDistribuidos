package dtos;

public class ComercioDTO {

    private Long id;
    private String nombre;
    private String usurio;
    private String tipo;

    public ComercioDTO() {}

    public ComercioDTO(String nombre, String authId, String tipo) {
        this.nombre = nombre;
        this.usurio = authId;
        this.tipo = tipo;
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
        return usurio;
    }

    public void setUsurio(String usurio) {
        this.usurio = usurio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
