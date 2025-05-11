package dtos;

public class ProductoDTO {
    private String nombre;
    private String categoria;
    private String descripcion;

    public ProductoDTO() {}

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
