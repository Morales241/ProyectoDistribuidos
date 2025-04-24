package servicios;

import entidades.Carrito;
import entidades.ProductoCarrito;
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

    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public List<Carrito> obtenerCarritosPorConsumidor(Long idConsumidor) {
        return carritoRepository.findByConsumidorId(idConsumidor);
    }

    public ProductoCarrito agregarProducto(Long idCarrito, ProductoCarrito productoCarrito) {
        productoCarrito.setIdCarrito(idCarrito);
        return productoCarritoRepository.save(productoCarrito);
    }

    public List<ProductoCarrito> obtenerProductos(Long idCarrito) {
        return productoCarritoRepository.findByCarritoId(idCarrito);
    }
}
