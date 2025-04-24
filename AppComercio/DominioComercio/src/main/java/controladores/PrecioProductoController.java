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

    @GetMapping
    public ResponseEntity<List<PrecioProducto>> findByComercioId(@RequestBody Comercio comercio) {
        return ResponseEntity.ok(precioProductoService.findByComercioId(comercio.getId()));
    }

    @GetMapping
    public ResponseEntity<List<PrecioProducto>> findByProductoId(@RequestBody Producto producto) {
        return ResponseEntity.ok(precioProductoService.findByProductoId(producto.getId()));
    }

    @GetMapping
    public ResponseEntity<Optional<PrecioProducto>> findEspecificPrecioProducto(@PathVariable Producto producto, @PathVariable Comercio comercio) {

        return ResponseEntity.ok(precioProductoService.findEspecificPrecioProducto(producto.getId(), comercio.getId()));
    }

    @PostMapping
    public void addPrecioProducto(@RequestBody PrecioProducto precioProducto) {
        precioProductoService.crearPrecioProducto(precioProducto);
    }

    @PostMapping
    public void removePrecioProducto(@RequestBody PrecioProducto precioProducto) {
        precioProductoService.eliminarPrecioProducto(precioProducto);
    }

}
