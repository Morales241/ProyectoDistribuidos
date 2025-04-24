package servicios;

import entidades.WishList;
import excepciones.ConsumidorServiciosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.WishListRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    public WishList agregarAWishlist(WishList wish) throws ConsumidorServiciosException {
        // Evitar duplicados
        if (wishListRepository.existsByConsumidorIdAndProductoId(wish.getConsumidor().getId(), wish.getIdProducto())) {
            throw new ConsumidorServiciosException("El producto ya se encuentra en la wishlist");
        }

        wish.setFechaAgregado(LocalDateTime.now());
        return wishListRepository.save(wish);
    }

    public List<WishList> obtenerWishlist(Long idConsumidor) {
        return wishListRepository.findByConsumidorId(idConsumidor);
    }

    public void eliminarWishlistItem(Long id) {
        wishListRepository.deleteById(id);
    }
}
