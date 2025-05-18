package dtos;

import java.time.LocalDateTime;

public class ReporteDTO {

    private PrecioProductoDTO Producto;

    private String contenido;

    private LocalDateTime fecha;

    private ConsumidorDTO consumidor;

    public ReporteDTO() {}

    public PrecioProductoDTO getProducto() {
        return Producto;
    }

    public void setProducto(PrecioProductoDTO producto) {
        Producto = producto;
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
