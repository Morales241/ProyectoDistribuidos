package dtos;

import java.time.LocalDateTime;

public class ReporteDTO {


    private PrecioProductoDTO producto;

    private String contenido;

    private LocalDateTime fecha;

    private Long consumidor;

    public ReporteDTO() {}


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

    public Long getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Long consumidor) {
        this.consumidor = consumidor;
    }
}
