package dtos;

import java.time.LocalDateTime;

public class ResenaDTO {

    private String contenido;

    private ConsumidorDTO consumidor;

    private int calificacion;

    private LocalDateTime fecha;

    private PrecioProductoDTO precioProducto;

    public ResenaDTO() {}

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public PrecioProductoDTO getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(PrecioProductoDTO precioProducto) {
        this.precioProducto = precioProducto;
    }
}
