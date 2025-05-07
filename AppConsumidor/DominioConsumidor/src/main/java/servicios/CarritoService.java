package servicios;

import entidades.Carrito;
import entidades.ProductoCarrito;
import entidades.WishList;
import entidades.ProductoWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.CarritoProductoRepository;
import repositorios.CarritoRepository;

import java.util.List;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository CarritoRepository;

    @Autowired
    private CarritoProductoRepository CPR;

    public Carrito getCarrito(Long idConsumidor){
        Carrito carrito = CarritoRepository.findByConsumidorId(idConsumidor);
        if(carrito.getProductos().isEmpty()){
            carrito.setProductos(CPR.findByCarrito(carrito));
        }
        return carrito;
    }

    public Carrito saveCarrito(Carrito carrito){
        return CarritoRepository.save(carrito);
    }

    public void agregarProductoACarrito(ProductoCarrito productoCarrito){
        CPR.save(productoCarrito);
    }

}
