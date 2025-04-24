package controladores;

import entidades.Carrito;
import entidades.ProductoCarrito;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.CarritoRepository;
import repositorios.ProductoCarritoRepository;

import java.util.List;

@RestController
@RequestMapping("/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoRepository carritoRepository;
    private final ProductoCarritoRepository productoCarritoRepository;

    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoRepository.save(carrito));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Carrito>> obtenerCarritosPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(carritoRepository.findByConsumidorId(idConsumidor));
    }

    @PostMapping("/{idCarrito}/productos")
    public ResponseEntity<ProductoCarrito> agregarProducto(
            @PathVariable Long idCarrito,
            @RequestBody ProductoCarrito productoCarrito) {

        productoCarrito.setIdCarrito(idCarrito);
        return ResponseEntity.ok(productoCarritoRepository.save(productoCarrito));
    }

    @GetMapping("/{idCarrito}/productos")
    public ResponseEntity<List<ProductoCarrito>> obtenerProductos(@PathVariable Long idCarrito) {
        return ResponseEntity.ok(productoCarritoRepository.findByCarritoId(idCarrito));
    }
}
