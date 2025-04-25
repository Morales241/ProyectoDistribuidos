package servicios;

import entidades.WishList;
import entidades.ProductoWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.CarritoRepository;
import repositorios.ProductoCarritoRepository;

import java.util.List;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoCarritoRepository productoCarritoRepository;

    public WishList crearCarrito(WishList wishList) {
        return carritoRepository.save(wishList);
    }

    public List<WishList> obtenerCarritosPorConsumidor(Long idConsumidor) {
        return carritoRepository.findByConsumidorId(idConsumidor);
    }

    public ProductoWishList agregarProducto(Long idCarrito, ProductoWishList productoWishList) {
        productoWishList.setIdWishList(idCarrito);
        return productoCarritoRepository.save(productoWishList);
    }

    public List<ProductoWishList> obtenerProductos(Long idCarrito) {
        return productoCarritoRepository.findByCarritoId(idCarrito);
    }
}
