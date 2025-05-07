package convertidores;

import dtos.*;
import entidades.Consumidor;
import entidades.ProductoWishList;
import entidades.WishList;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConvertidorWishList extends Convertidor<WishListDTO, WishList> {

    private static Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    public ConvertidorWishList() {
        super(ConvertidorWishList::convertToEntity, ConvertidorWishList::convertToDTO);
    }

    private static WishListDTO convertToDTO(WishList wishList) {
        //tienes que buscar el producto para rellenar la info, pero no se como hacerlo en un metodo estatico jsjs
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setNombre(wishList.getNombre());
        wishListDTO.setConsumidor(convertidorConsumidor.convertFromEntity(wishList.getConsumidor()));
        wishListDTO.setProductos(wishList.getProductos().stream().map(ConvertidorWishListProducto::convertToDto).collect(Collectors.toList()));
        return wishListDTO;
    }

    private static WishList convertToEntity(WishListDTO dto){
        //lo mismo
        WishList wishList = new WishList();
        wishList.setConsumidor(convertidorConsumidor.convertFromDto(dto.getConsumidor()));
        wishList.setNombre(dto.getNombre());
        wishList.setProductos(dto.getProductos().stream().map(ConvertidorWishListProducto::convertToEntity).collect(Collectors.toList()));
        return wishList;

    }

    @Component
    private class ConvertidorWishListProducto extends Convertidor<ProductoWishListDTO, ProductoWishList>{

        @Autowired
        private static ComercioClient comercioClient;


        public ConvertidorWishListProducto() {
            super(ConvertidorWishListProducto::convertToEntity, ConvertidorWishListProducto::convertToDto);

        }

        private static ProductoWishList convertToEntity(ProductoWishListDTO dto){
            ProductoWishList productoWishList = new ProductoWishList();
            productoWishList.setCantidad(dto.getCantidad());
            return productoWishList;
        }

        private static ProductoWishListDTO convertToDto(ProductoWishList entity){
            ProductoWishListDTO productoWishListDTO = new ProductoWishListDTO();
            productoWishListDTO.setCantidad(entity.getCantidad());
            return productoWishListDTO;
        }
    }
}
