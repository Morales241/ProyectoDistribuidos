package controladores;

import entidades.WishList;
import excepciones.ConsumidorServiciosException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.WishListRepository;
import servicios.WishListService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/wishList")
public class WishListController {

    @Autowired
    private WishListService servicio;

    @PostMapping
    public ResponseEntity<WishList> agregarAWishlist(@RequestBody WishList wish) {
        try {
            wish.setFechaAgregado(LocalDateTime.now());
            WishList wishList = servicio.agregarAWishlist(wish);
            return ResponseEntity.ok(wishList);
        } catch (ConsumidorServiciosException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{idConsumidor}")
    public ResponseEntity<List<WishList>> obtenerWishlist(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerWishlist(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarWishlistItem(@PathVariable Long id) {
        servicio.eliminarWishlistItem(id);
        return ResponseEntity.noContent().build();
    }
}
