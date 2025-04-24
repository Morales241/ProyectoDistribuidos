package controladores;

import entidades.Comercio;
import entidades.PrecioProducto;
import entidades.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.PrecioProductoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/precioProductos")
public class PrecioProductoController {

    @Autowired
    private PrecioProductoService precioProductoService;

    @GetMapping("/buscarPorComercioId/{comercioid}")
    public ResponseEntity<List<PrecioProducto>> findByComercioId(@PathVariable Long comercioid) {
        return ResponseEntity.ok(precioProductoService.findByComercioId(comercioid));
    }

    @GetMapping("/buscarPorProductoId/{productoid}")
    public ResponseEntity<List<PrecioProducto>> findByProductoId(@PathVariable  Long productoid) {
        return ResponseEntity.ok(precioProductoService.findByProductoId(productoid));
    }

    @GetMapping("/buscarEspecificamente/{productoid}/{comercioid}")
    public ResponseEntity<PrecioProducto> findEspecificPrecioProducto(@PathVariable Long productoid, @PathVariable Long comercioid) {
        return precioProductoService.findEspecificPrecioProducto(productoid, comercioid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/guardar")
    public ResponseEntity<PrecioProducto> addPrecioProducto(@RequestBody PrecioProducto precioProducto) {
        return ResponseEntity.ok(precioProductoService.crearPrecioProducto(precioProducto));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removePrecioProducto(@PathVariable Long precioProductoid) {
        precioProductoService.eliminarPrecioProducto(precioProductoid);
        return ResponseEntity.noContent().build();
    }

}
