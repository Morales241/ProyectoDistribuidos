package servicios;

import entidades.Consumidor;
import entidades.ProductoWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.WishListProductoRepository;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListProductoRepository wishListRepository;

    public List<ProductoWishList> getwishList() {
        return wishListRepository.findAll();
    }

    public List<ProductoWishList> traerWishListPorConsumidor(Consumidor consumidor) {
        return wishListRepository.findByConsumidor(consumidor);
    }

    public ProductoWishList traerWishListPorId(Long id) {
        return wishListRepository.findById(id).get();
    }

    public List<ProductoWishList> traerPorComercioId(Long id) {
        return wishListRepository.findByIdComercio(id);
    }

    public ProductoWishList guardarWishList(ProductoWishList productoWishList) {
        return wishListRepository.save(productoWishList);
    }

}
