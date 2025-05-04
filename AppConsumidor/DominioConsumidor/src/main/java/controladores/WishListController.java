package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorCarrito;
import dtos.CarritoDTO;
import dtos.PrecioProductoDTO;
import entidades.Carrito;
import excepciones.ConsumidorServiciosException;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.WishListService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/wishList")
public class WishListController {


    @Autowired
    private WishListService servicio;

    @Autowired
    private ComercioClient clienteComercio;

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
    public ResponseEntity<List<CarritoDTO>> obtenerWishlist(@PathVariable Long idConsumidor) {
        Convertidor<CarritoDTO, Carrito> convertidor = new ConvertidorCarrito();
        List<CarritoDTO> dtos = convertidor.createFromEntities(servicio.obtenerWishlist(idConsumidor));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<List<CarritoDTO>> obtenerWishlistPorProducto(@PathVariable Long idProducto) {
        Convertidor<CarritoDTO, Carrito> convertidor = new ConvertidorCarrito();
        List<CarritoDTO> dtos = convertidor.createFromEntities(servicio.obtenerWishlistPorProducto(idProducto));
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarWishlistItem(@PathVariable Long id) {
        servicio.eliminarWishlistItem(id);
        return ResponseEntity.noContent().build();
    }
}