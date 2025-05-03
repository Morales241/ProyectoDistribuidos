package controladores;

import entidades.WishList;
import entidades.ProductoWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.CarritoService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoService servicio;

    @PostMapping
    public ResponseEntity<WishList> crearCarrito(@RequestBody WishList wishList) {
        return ResponseEntity.ok(servicio.crearCarrito(wishList));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<WishList>> obtenerCarritosPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerCarritosPorConsumidor(idConsumidor));
    }

    @PostMapping("/{idCarrito}/productos")
    public ResponseEntity<ProductoWishList> agregarProducto(
            @PathVariable Long idCarrito,
            @RequestBody ProductoWishList productoWishList) {

        return ResponseEntity.ok(servicio.agregarProducto(idCarrito, productoWishList));
    }

    @GetMapping("/{idCarrito}/productos")
    public ResponseEntity<List<ProductoWishList>> obtenerProductos(@PathVariable Long idCarrito) {
        return ResponseEntity.ok(servicio.obtenerProductos(idCarrito));
    }
}
