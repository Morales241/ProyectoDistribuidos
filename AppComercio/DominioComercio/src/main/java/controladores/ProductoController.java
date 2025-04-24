package controladores;

import entidades.Producto;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ProductoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<Producto> buscarProductoPornombre(@RequestParam String nombre) {
        return productoService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorCategoria")
    public ResponseEntity<List<Producto>> buscarProductoPorCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(productoService.findByCategoria(categoria));
    }

    @GetMapping("/buscarPorConisidencias")
    public ResponseEntity<List<Producto>> buscarCoincidenciasPorNombre(@RequestParam String cadena) {
        return ResponseEntity.ok(productoService.findByNombreLike(cadena));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.saveProducto(producto));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long productoid) {
        productoService.removeProducto(productoid);
        return ResponseEntity.ok().build();
    }
}
