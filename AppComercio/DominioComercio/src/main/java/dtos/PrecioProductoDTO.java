package dtos;

import java.time.LocalDateTime;

public class PrecioProductoDTO {
    private Long id;
    private Long comercio;
    private String nombreComercio;
    private Long producto;
    private String nombreProducto;
    private double precio;
    private LocalDateTime fecha;

    public PrecioProductoDTO() {}

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComercio() {
        return comercio;
    }

    public void setComercio(Long Comercio) {
        this.comercio = Comercio;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long Producto) {
        this.producto = Producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
