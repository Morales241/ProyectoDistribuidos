package controladores;

import entidades.Carrito;
import entidades.ProductoCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.CarritoService;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoService servicio;

    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(servicio.crearCarrito(carrito));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Carrito>> obtenerCarritosPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerCarritosPorConsumidor(idConsumidor));
    }

    @PostMapping("/{idCarrito}/productos")
    public ResponseEntity<ProductoCarrito> agregarProducto(
            @PathVariable Long idCarrito,
            @RequestBody ProductoCarrito productoCarrito) {

        return ResponseEntity.ok(servicio.agregarProducto(idCarrito, productoCarrito));
    }

    @GetMapping("/{idCarrito}/productos")
    public ResponseEntity<List<ProductoCarrito>> obtenerProductos(@PathVariable Long idCarrito) {
        return ResponseEntity.ok(servicio.obtenerProductos(idCarrito));
    }
}
