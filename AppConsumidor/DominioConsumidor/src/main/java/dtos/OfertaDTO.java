package dtos;

import java.time.LocalDateTime;

public class OfertaDTO {
    private String comercio;
    private String producto;
    private Double precioOferta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String descripcion;

    public OfertaDTO() {}

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

}
