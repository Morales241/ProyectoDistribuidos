package servicios;

import entidades.Carrito;
import entidades.ProductoCarrito;
import entidades.WishList;
import entidades.ProductoWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.CarritoProductoRepository;
import repositorios.CarritoRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository CarritoRepository;

    @Autowired
    private CarritoProductoRepository CPR;

    public Carrito getCarrito(Long idConsumidor) {
        Carrito carrito = CarritoRepository.findByConsumidorId(idConsumidor);

        return carrito;
    }

    public Carrito saveCarrito(Carrito carrito) {
        return CarritoRepository.save(carrito);
    }

    public List<Carrito> getCarritosPorProducto(long idProducto) {
        List<Carrito> carritos = new ArrayList<>();

        Iterator iterator = CarritoRepository.findAll().iterator();

        while (iterator.hasNext()) {
            Carrito carrito = (Carrito) iterator.next();

            carrito.getProductos().forEach(productoCarrito -> {

                if (productoCarrito.getIdPrecioProducto() == idProducto) {
                    carritos.add(carrito);

                }
            });
        }
        return carritos;
    }
}
