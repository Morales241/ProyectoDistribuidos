package dtos;

import java.time.LocalDateTime;

public class CarritoDTO {

    private ConsumidorDTO consumidor;

    private PrecioProductoDTO producto;

    private LocalDateTime fecha;

    public CarritoDTO() {}

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public PrecioProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(PrecioProductoDTO producto) {
        this.producto = producto;
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }
}


