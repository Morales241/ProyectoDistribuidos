package convertidores;

import dtos.*;
import entidades.Carrito;
import entidades.Consumidor;
import entidades.ProductoCarrito;
import entidades.ProductoWishList;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConvertidorCarrito extends Convertidor<CarritoDTO, Carrito> {

    @Autowired
    private static Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();


    public ConvertidorCarrito() {
        super(ConvertidorCarrito::convertToEntity, ConvertidorCarrito::convertToDto);
    }

    private static CarritoDTO convertToDto(Carrito carrito) {
        //tenemos que buscar e insertar los productos fuera y el carrito dentro de los productos, basicamente lo mismo que el wishlist
        CarritoDTO dto = new CarritoDTO();
        dto.setConsumidor(convertidorConsumidor.convertFromEntity(carrito.getConsumidor()));
        dto.setProductos(carrito.getProductos().stream().map(ConvertidorCarritoProducto::convertToDto).collect(Collectors.toList()));
        return dto;
    }

    private static Carrito convertToEntity(CarritoDTO dto) {
        //ps lo mismo que ya sabes
        Carrito carrito = new Carrito();
        carrito.setConsumidor(convertidorConsumidor.convertFromDto(dto.getConsumidor()));
        carrito.setProductos(dto.getProductos().stream().map(ConvertidorCarritoProducto::convertToEntity).collect(Collectors.toList()));
        return carrito;
    }

    @Component
    private class ConvertidorCarritoProducto extends Convertidor<CarritoProductoDTO, ProductoCarrito>{

        @Autowired
        private static ComercioClient comercioClient;

        public ConvertidorCarritoProducto() {
            super(ConvertidorCarritoProducto::convertToEntity, ConvertidorCarritoProducto::convertToDto );
        }

        private static ProductoCarrito convertToEntity(CarritoProductoDTO dto){
            ProductoCarrito productoCarrito = new ProductoCarrito();
            productoCarrito.setCantidad(dto.getCantidad());
            return productoCarrito;
        }

        private static CarritoProductoDTO convertToDto(ProductoCarrito entity){
            CarritoProductoDTO carritoProductoDTO = new CarritoProductoDTO();
            carritoProductoDTO.setCantidad(entity.getCantidad());
            return carritoProductoDTO;
        }
    }
}
