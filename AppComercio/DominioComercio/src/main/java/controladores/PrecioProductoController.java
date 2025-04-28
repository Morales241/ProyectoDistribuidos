package controladores;

import dtos.PrecioProductoDTO;
import entidades.Comercio;
import entidades.PrecioProducto;
import entidades.Producto;
import mappers.PrecioProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;
import servicios.PrecioProductoService;
import servicios.ProductoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/precioProductos")
public class PrecioProductoController {

    @Autowired
    private PrecioProductoService precioProductoService;
    @Autowired
    private ComercioService comercioService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/buscarPorComercioId/{comercioid}")
    public ResponseEntity<List<PrecioProductoDTO>> findByComercioId(@PathVariable Long comercioid) {
        List<PrecioProducto> pps = precioProductoService.findByComercioId(comercioid);
        List<PrecioProductoDTO> ppsdto = pps.stream().map(precioProducto -> PrecioProductoMapper.toDTO(precioProducto, productoService, comercioService)).collect(Collectors.toList());
        return ResponseEntity.ok(ppsdto);
    }

    @GetMapping("/buscarPorProductoId/{productoid}")
    public ResponseEntity<List<PrecioProductoDTO>> findByProductoId(@PathVariable Long productoid) {
        List<PrecioProducto> pps = precioProductoService.findByProductoId(productoid);
        List<PrecioProductoDTO> ppsdto = pps.stream().map(precioProducto -> PrecioProductoMapper.toDTO(precioProducto, productoService, comercioService)).collect(Collectors.toList());
        return ResponseEntity.ok(ppsdto);
    }

    @GetMapping("/buscarEspecificamente/{productoid}/{comercioid}")
    public ResponseEntity<PrecioProductoDTO> findEspecificPrecioProducto(@PathVariable Long productoid, @PathVariable Long comercioid) {
        Optional<PrecioProducto> pp = precioProductoService.findEspecificPrecioProducto(productoid, comercioid);
        return pp.map(precioProducto -> PrecioProductoMapper.toDTO(precioProducto, productoService, comercioService)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> addPrecioProducto(@RequestBody PrecioProductoDTO precioProductoDTO) {

        Optional<PrecioProducto> precioProductoExistente = precioProductoService.findEspecificPrecioProducto(
                precioProductoDTO.getProducto().getId(),
                precioProductoDTO.getComercio().getId()
        );

        if (precioProductoExistente.isPresent()) {
            return ResponseEntity.badRequest().body("Ya se registró un precio para este producto en este comercio.");
        } else {
            return ResponseEntity.ok(PrecioProductoMapper.toDTO(precioProductoService.crearPrecioProducto(PrecioProductoMapper.toEntity(precioProductoDTO)), productoService, comercioService));
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removePrecioProducto(@PathVariable Long precioProductoid) {
        precioProductoService.eliminarPrecioProducto(precioProductoid);
        return ResponseEntity.noContent().build();
    }

}
