package dtos;

import java.time.LocalDateTime;

public class PrecioProductoDTO {
    private Long id;
    private ComercioDTO Comercio;
    private ProductoDTO Producto;
    private double precio;
    private LocalDateTime fecha;

    public PrecioProductoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComercioDTO getComercio() {
        return Comercio;
    }

    public void setComercio(ComercioDTO Comercio) {
        this.Comercio = Comercio;
    }

    public ProductoDTO getProducto() {
        return Producto;
    }

    public void setProducto(ProductoDTO Producto) {
        this.Producto = Producto;
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
