package controladores;

import entidades.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.WishListRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
public class WishListController {

    private final WishListRepository wishListRepository;

    @PostMapping
    public ResponseEntity<WishList> agregarAWishlist(@RequestBody WishList wish) {
        // Evitar duplicados
        if (wishListRepository.existsByConsumidorIdAndProductoId(wish.getConsumidor().getId(), wish.getIdProducto())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        wish.setFechaAgregado(LocalDateTime.now());
        return ResponseEntity.ok(wishListRepository.save(wish));
    }

    @GetMapping("/{idConsumidor}")
    public ResponseEntity<List<WishList>> obtenerWishlist(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(wishListRepository.findByConsumidorId(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarWishlistItem(@PathVariable Long id) {
        wishListRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
