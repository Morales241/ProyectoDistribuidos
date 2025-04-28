package convertidores;

import dtos.CarritoDTO;
import dtos.ConsumidorDTO;
import dtos.PrecioProductoDTO;
import entidades.Carrito;
import entidades.Consumidor;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConvertidorCarrito extends Convertidor<CarritoDTO, Carrito> {

    @Autowired
    private static ComercioClient clienteComercio;
    private static Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    public ConvertidorCarrito() {
        super(ConvertidorCarrito::convertToEntity, ConvertidorCarrito::convertToDto);
    }

    private static CarritoDTO convertToDto(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setFecha(carrito.getFechaAgregado());
        dto.setConsumidor(convertidorConsumidor.convertFromEntity(carrito.getConsumidor()));
        List<PrecioProductoDTO> productos = clienteComercio.findByProductoId(carrito.getProductoId()).getBody();
        PrecioProductoDTO producto = productos.getFirst();
        dto.setProducto(producto);

        return dto;
    }

    private static Carrito convertToEntity(CarritoDTO dto) {
        Carrito carrito = new Carrito();
        carrito.setFechaAgregado(dto.getFecha());
        carrito.setConsumidor(convertidorConsumidor.convertFromDto(dto.getConsumidor()));

        return carrito;
    }
}
