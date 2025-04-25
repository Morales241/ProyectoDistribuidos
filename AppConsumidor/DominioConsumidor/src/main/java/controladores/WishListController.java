package controladores;

import entidades.Carrito;
import excepciones.ConsumidorServiciosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.WishListService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/wishList")
public class WishListController {

    @Autowired
    private WishListService servicio;

    @PostMapping
    public ResponseEntity<Carrito> agregarAWishlist(@RequestBody Carrito wish) {
        try {
            wish.setFechaAgregado(LocalDateTime.now());
            Carrito carrito = servicio.agregarAWishlist(wish);
            return ResponseEntity.ok(carrito);
        } catch (ConsumidorServiciosException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{idConsumidor}")
    public ResponseEntity<List<Carrito>> obtenerWishlist(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerWishlist(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarWishlistItem(@PathVariable Long id) {
        servicio.eliminarWishlistItem(id);
        return ResponseEntity.noContent().build();
    }
}