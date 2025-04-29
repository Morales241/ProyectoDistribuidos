package dtos;

import java.time.LocalDateTime;

public class PrecioProductoDTO {
    private Long id;
    private Long comercio;
    private Long producto;
    private double precio;
    private LocalDateTime fecha;

    public PrecioProductoDTO() {}

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
