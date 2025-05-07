package servicios;

import entidades.WishList;
import excepciones.ConsumidorServiciosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.WishListProductoRepository;
import repositorios.WishListRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishListProductoRepository WLPR;

    public WishList ObtenerWishListPorNombre(String nombre, Long consumidor) throws ConsumidorServiciosException {
        WishList wishList = wishListRepository.findByNombreAndConsumidorId(nombre, consumidor);

        if (wishList.getProductos().isEmpty()) {
            wishList.setProductos(WLPR.findByWishListId(wishList.getId()));
        }
        return wishList;
    }

    public List<WishList> ObtenerWishListsPorConsumidor(Long consumidor) throws ConsumidorServiciosException {
        List<WishList> wishLists = new ArrayList<>();
        wishLists = wishListRepository.findByConsumidorId(consumidor);

        wishLists.forEach(wishList -> {wishList.setProductos(WLPR.findByWishListId(wishList.getId()));});

        return wishLists;
    }

    public WishList save(WishList wishList) throws ConsumidorServiciosException {
        return wishListRepository.save(wishList);
    }

    public void remove(String nombre, Long consumidor){

        WishList wishList = wishListRepository.findByNombreAndConsumidorId(nombre, consumidor);
        wishListRepository.delete(wishList);
    }
}
