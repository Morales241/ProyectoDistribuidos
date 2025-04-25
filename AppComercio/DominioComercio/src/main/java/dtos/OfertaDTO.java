package dtos;

import java.time.LocalDateTime;

public class OfertaDTO {
    private Long id;
    private ComercioDTO comercio;
    private ProductoDTO producto;
    private Double precioOferta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String descripcion;

    public OfertaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComercioDTO getComercio() {
        return comercio;
    }

    public void setComercio(ComercioDTO comercioP) {
        this.comercio = comercioP;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO productoP) {
        this.producto = productoP;
    }

    public Double getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(Double precioOferta) {
        this.precioOferta = precioOferta;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
