package convertidores;

import dtos.*;
import entidades.Consumidor;
import entidades.ProductoWishList;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConvertidorWishList extends Convertidor<ProductoWishListDTO, ProductoWishList> {

    private static Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    public ConvertidorWishList() {
        super(ConvertidorWishList::convertToEntity, ConvertidorWishList::convertToDTO);
    }

    private static ProductoWishListDTO convertToDTO(ProductoWishList wishList) {

        ProductoWishListDTO wishListDTO = new ProductoWishListDTO();
        wishListDTO.setFecha(wishList.getFecha());
        wishListDTO.setSugeriencia(wishList.getSugeriencia());
        return wishListDTO;
    }

    private static ProductoWishList convertToEntity(ProductoWishListDTO dto){

        ProductoWishList wishList = new ProductoWishList();
        wishList.setFecha(dto.getFecha());
        wishList.setSugeriencia(dto.getSugeriencia());
        return wishList;

    }

}
