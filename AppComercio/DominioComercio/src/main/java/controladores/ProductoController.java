package controladores;

import entidades.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import servicios.ProductoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping
    public ResponseEntity<Optional<Producto>> buscarProductoPornombre(String nombre) {
        return ResponseEntity.ok(productoService.findByNombre(nombre));
    }

    @GetMapping
    public ResponseEntity<List<Producto>> buscarProductoPorCategoria(String categoria) {
        return ResponseEntity.ok(productoService.findByCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<List<Producto>> buscarCoincidenciasPorNombre(String cadena) {
        return ResponseEntity.ok(productoService.findByNombreLike(cadena));
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(Producto producto) {
        return ResponseEntity.ok(productoService.saveProducto(producto));
    }

    @PostMapping
    public void eliminarProducto(Producto producto) {
        productoService.removeProducto(producto);
    }
}
