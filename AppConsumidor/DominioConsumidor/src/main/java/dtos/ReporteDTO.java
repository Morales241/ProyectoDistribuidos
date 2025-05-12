package dtos;

import java.time.LocalDateTime;

public class ReporteDTO {

    private ComercioDTO comercio;

    private PrecioProductoDTO producto;

    private String contenido;

    private LocalDateTime fecha;

    private ConsumidorDTO consumidor;

    public ReporteDTO() {}

    public ComercioDTO getComercio() {
        return comercio;
    }

    public void setComercio(ComercioDTO comercio) {
        this.comercio = comercio;
    }

    public PrecioProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(PrecioProductoDTO producto) {
        this.producto = producto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }
}
