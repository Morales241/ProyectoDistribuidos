package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorCarrito;
import convertidores.ConvertidorConsumidor;
import dtos.*;
import entidades.Carrito;
import entidades.Consumidor;
import entidades.ProductoCarrito;
import entidades.ProductoWishList;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.CarritoService;
import servicios.ConsumidorService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoService servicio;

    Convertidor<CarritoDTO, Carrito> convertidorCarrito = new ConvertidorCarrito();

    Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    @Autowired
    private ConsumidorService servicioConsumidor;

    @Autowired
    private ComercioClient comercioClient;

    @PostMapping("/CrerCarrito/{idConsumidor}")
    public ResponseEntity<CarritoDTO> crearCarrito(@PathVariable Long idConsumidor) {
        Optional<Consumidor> consumidor = servicioConsumidor.obtener(idConsumidor);

        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setConsumidor(convertidorConsumidor.convertFromEntity(consumidor.get()));

        servicio.saveCarrito(convertidorCarrito.convertFromDto(carritoDTO));

        return ResponseEntity.ok(carritoDTO);
    }

    @GetMapping("/obtenerCarrito/{idConsumidor}")
    public ResponseEntity<CarritoDTO> obtenerCarritoPorConsumidor(@PathVariable Long idConsumidor) {
        CarritoDTO carritoDTO = new CarritoDTO();
        Carrito carrito = servicio.getCarrito(idConsumidor);

        carritoDTO = convertidorCarrito.convertFromEntity(carrito);

        if (!carrito.getProductos().isEmpty()) {
            List<CarritoProductoDTO> productosDTO = carrito.getProductos().stream().map(productoCarrito -> {
                PrecioProductoDTO producto = comercioClient.traerProductoEspecificoPorId(productoCarrito.getIdPrecioProducto()).getBody();
                CarritoProductoDTO carritoProductoDTO = new CarritoProductoDTO();
                carritoProductoDTO.setProducto(producto);
                carritoProductoDTO.setCantidad(productoCarrito.getCantidad());
                return carritoProductoDTO;
            }).collect(Collectors.toList());

            carritoDTO.setProductos(productosDTO);
        }


        return ResponseEntity.ok(carritoDTO);
    }

    @PostMapping("/agregarACarrito/{idConsumidor}")
    public ResponseEntity<Void> agregarProducto(
            @PathVariable Long idConsumidor,
            @RequestBody CarritoProductoDTO productoDTO) {

        Carrito carrito = servicio.getCarrito(idConsumidor);
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }

        boolean encontrado = false;

        for (ProductoCarrito producto : carrito.getProductos()) {
            Long productoAux = comercioClient.findEspecificIDPrecioProducto(productoDTO.getProducto().getProducto(), productoDTO.getProducto().getComercio()).getBody();

            if (productoAux != null && producto.getIdPrecioProducto() == productoAux) {
                producto.setCantidad(producto.getCantidad() + productoDTO.getCantidad());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            Long productoAux = comercioClient.findEspecificIDPrecioProducto(productoDTO.getProducto().getProducto(), productoDTO.getProducto().getComercio()).getBody();

            if (productoAux == null) {
                return ResponseEntity.badRequest().build();
            }

            ProductoCarrito nuevoProducto = new ProductoCarrito();
            nuevoProducto.setCantidad(productoDTO.getCantidad());
            nuevoProducto.setIdPrecioProducto(productoAux);
            nuevoProducto.setCarrito(carrito);
            carrito.getProductos().add(nuevoProducto);
        }

        servicio.saveCarrito(carrito);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/eliminarDeCarrito/{idConsumidor}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable Long idConsumidor, @RequestBody CarritoProductoDTO productoDTO) {

        Carrito carrito = servicio.getCarrito(idConsumidor);
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }

        boolean encontrado = false;
        Iterator<ProductoCarrito> iterator = carrito.getProductos().iterator();

        while (iterator.hasNext()) {
            ProductoCarrito producto = iterator.next();
            PrecioProductoDTO productoAux = comercioClient.traerProductoEspecificoPorId(producto.getIdPrecioProducto()).getBody();
            if (productoAux == null) continue;

            if (productoAux.getProducto().equals(productoDTO.getProducto().getProducto()) &&
                    productoAux.getComercio().equals(productoDTO.getProducto().getComercio())) {

                if (producto.getCantidad() <= productoDTO.getCantidad()) {
                    iterator.remove();
                } else {
                    producto.setCantidad(producto.getCantidad() - productoDTO.getCantidad());
                }

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return ResponseEntity.badRequest().build();
        }

        servicio.saveCarrito(carrito);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/carritos/obtenerPorProducto/{idProducto}")
    public ResponseEntity<List<CarritoDTO>> obtenerCarritosPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(convertidorCarrito.createFromEntities(servicio.getCarritosPorProducto(idProducto)));
    }
}