package dtos;

import entidades.ProductoCarrito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarritoDTO {

    private ConsumidorDTO consumidor;

    List<CarritoProductoDTO> productos;

    public CarritoDTO() {
        productos = new ArrayList<>();
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }

    public List<CarritoProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<CarritoProductoDTO> productos) {
        this.productos = productos;
    }
}


