package controladores;

import dtos.ComercioDTO;
import dtos.PrecioProductoDTO;
import dtos.ProductoDTO;
import entidades.Comercio;
import entidades.PrecioProducto;
import entidades.Producto;
import mappers.ComercioMapper;
import mappers.PrecioProductoMapper;
import mappers.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;
import servicios.PrecioProductoService;
import servicios.ProductoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
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
        System.out.println("llego el id de comercio: " + comercioid);
        List<PrecioProducto> pps = precioProductoService.findByComercioId(comercioid);

        List<PrecioProductoDTO> ppsdto = pps.stream().map(precioProducto -> PrecioProductoMapper.toDTO(precioProducto)).collect(Collectors.toList());

        ppsdto.forEach(productoDTO -> {

            productoDTO.setNombreProducto(traerProductoPorId(productoDTO.getProducto()).getNombre());

            productoDTO.setNombreComercio(traerComercioPorId(productoDTO.getComercio()).getNombre());
        });

        return ResponseEntity.ok(ppsdto);
    }

    @GetMapping("/buscarPorProductoId/{productoid}")
    public ResponseEntity<List<PrecioProductoDTO>> findByProductoId(@PathVariable Long productoid) {

        List<PrecioProducto> pps = precioProductoService.findByProductoId(productoid);
        List<PrecioProductoDTO> ppsdto = pps.stream().map(precioProducto -> PrecioProductoMapper.toDTO(precioProducto)).collect(Collectors.toList());

        ppsdto.forEach(productoDTO -> {

            productoDTO.setNombreProducto(traerProductoPorId(productoDTO.getProducto()).getNombre());

            productoDTO.setNombreComercio(traerComercioPorId(productoDTO.getComercio()).getNombre());
        });

        return ResponseEntity.ok(ppsdto);
    }

    private ProductoDTO traerProductoPorId(Long productoid){

        return ProductoMapper.toDTO(productoService.findById(productoid));
    }

    private ComercioDTO traerComercioPorId(Long comercioid){
        System.out.println("va a buscar el comercio por el id:"+comercioid);
        return ComercioMapper.toDTO(comercioService.buscarComercioPorId(comercioid));
    }

    @GetMapping("/buscarEspecificamente/{productoid}/{comercioid}")
    public ResponseEntity<PrecioProductoDTO> findEspecificPrecioProducto(@PathVariable Long productoid, @PathVariable Long comercioid) {
        Optional<PrecioProducto> pp = precioProductoService.findEspecificPrecioProducto(productoid, comercioid);
        PrecioProductoDTO ppDTO = PrecioProductoMapper.toDTO(pp.get());

        if (pp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ppDTO.setNombreProducto(traerProductoPorId(ppDTO.getProducto()).getNombre());

        ppDTO.setNombreComercio(traerComercioPorId(ppDTO.getComercio()).getNombre());

        return ResponseEntity.ok(ppDTO);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> addPrecioProducto(@RequestBody PrecioProductoDTO precioProductoDTO) {

        Optional<PrecioProducto> precioProductoExistente = precioProductoService.findEspecificPrecioProducto(
                precioProductoDTO.getProducto(),
                precioProductoDTO.getComercio()
        );

        if (precioProductoExistente.isPresent()) {
            return ResponseEntity.badRequest().body("Ya se registró un precio para este producto en este comercio.");
        } else {
            precioProductoDTO.setFecha(LocalDateTime.now());
            return ResponseEntity.ok(PrecioProductoMapper.toDTO(precioProductoService.crearPrecioProducto(PrecioProductoMapper.toEntity(precioProductoDTO))));
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removePrecioProducto(@PathVariable Long precioProductoid) {
        precioProductoService.eliminarPrecioProducto(precioProductoid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PrecioProducto> modificarPrecioProducto(@PathVariable Long idComercio, @PathVariable String nombre, @PathVariable double precio) {
        Optional<Producto> productoAux = productoService.findByNombre(nombre);
        Optional<PrecioProducto> precioProductoExistente = null;
        if (productoAux.isPresent()) {
            precioProductoExistente = precioProductoService.findEspecificPrecioProducto(productoAux.get().getId(), idComercio);
            if (precioProductoExistente.isPresent()) {
                precioProductoExistente.get().setPrecio(precio);
            }
        }
        return ResponseEntity.ok(precioProductoService.crearPrecioProducto(precioProductoExistente.get()));
    }
}
