package controladores;

import dtos.PrecioProductoDTO;
import entidades.Comercio;
import entidades.PrecioProducto;
import entidades.Producto;
import mappers.Convertidor;
import mappers.ConvertidorPrecioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;
import servicios.PrecioProductoService;
import servicios.ProductoService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/precioProductos")
public class PrecioProductoController {

    Convertidor<PrecioProductoDTO, PrecioProducto> convertidor = new ConvertidorPrecioProducto();

    @Autowired
    private PrecioProductoService precioProductoService;
    @Autowired
    private ComercioService comercioService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/buscarComercioPorNombre/{comercio}")
    public ResponseEntity<List<PrecioProductoDTO>> findByComercioId(@PathVariable String comercio) {
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(comercio);
        List<PrecioProducto> pps = precioProductoService.findByComercioId(comercioAux.get().getId());

        List<PrecioProductoDTO> ppsdto = convertidor.createFromEntities(pps);

        for(int i = 0 ; i < ppsdto.size() ; i++){
            Comercio comercioAux2 = traerComercio(pps.get(i).getComercio());
            Producto productoAux = traerProducto(pps.get(i).getProducto());
            ppsdto.get(i).setComercio(comercioAux2.getNombre());
            ppsdto.get(i).setProducto(productoAux.getNombre());
        }

        return ResponseEntity.ok(ppsdto);
    }

    @GetMapping("/buscarPorProductoId/{Nproducto}")
    public ResponseEntity<List<PrecioProductoDTO>> findByProductoId(@PathVariable String Nproducto) {
        Optional<Producto> productoAux = productoService.findByNombre(Nproducto);
        List<PrecioProducto> pps = precioProductoService.findByProductoId(productoAux.get().getId());
        List<PrecioProductoDTO> ppsdto = convertidor.createFromEntities(pps);

        for(int i = 0 ; i < ppsdto.size() ; i++){
            Comercio comercioAux = traerComercio(pps.get(i).getComercio());
            Producto productoAux2 = traerProducto(pps.get(i).getProducto());
            ppsdto.get(i).setComercio(comercioAux.getNombre());
            ppsdto.get(i).setProducto(productoAux2.getNombre());
        }

        return ResponseEntity.ok(ppsdto);
    }

    @GetMapping("/buscarEspecificamente/{Nproducto}/{comercioid}")
    public ResponseEntity<PrecioProductoDTO> findEspecificPrecioProducto(@PathVariable String Nproducto, @PathVariable String comercio) {
        Optional<Producto> productoAux = productoService.findByNombre(Nproducto);
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(comercio);

        Optional<PrecioProducto> pp = precioProductoService.findEspecificPrecioProducto(productoAux.get().getId(), comercioAux.get().getId());
        PrecioProductoDTO ppDTO = convertidor.convertFromEntity(pp.get());

        ppDTO.setProducto(productoAux.get().getNombre());
        ppDTO.setComercio(comercioAux.get().getNombre());

        if (pp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ppDTO);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> addPrecioProducto(@RequestBody PrecioProductoDTO precioProductoDTO) {
        Optional<Producto> productoAux = productoService.findByNombre(precioProductoDTO.getProducto());
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(precioProductoDTO.getComercio());

        Optional<PrecioProducto> precioProductoExistente = precioProductoService.findEspecificPrecioProducto(
                productoAux.get().getId(),
                comercioAux.get().getId()
        );

        if (precioProductoExistente.isPresent()) {
            return ResponseEntity.badRequest().body("Ya se registró un precio para este producto en este comercio.");
        } else {
            PrecioProducto nuevoPrecioProducto = convertidor.convertFromDto(precioProductoDTO);

            nuevoPrecioProducto.setProducto(traerProducto(precioProductoDTO.getProducto()).getId());
            nuevoPrecioProducto.setComercio(traerComercio(precioProductoDTO.getComercio()).getId());
            return ResponseEntity.ok(convertidor.convertFromEntity(precioProductoService.crearPrecioProducto(nuevoPrecioProducto)));
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removePrecioProducto(@RequestBody String comercio, @RequestBody String nombre) {
        Optional<Producto> productoAux = productoService.findByNombre(nombre);
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(comercio);
        Optional<PrecioProducto> precioProductoExistente = precioProductoService.findEspecificPrecioProducto(productoAux.get().getId(), comercioAux.get().getId());
        precioProductoService.eliminarPrecioProducto(precioProductoExistente.get().getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/modificarPrecio/{comercio}/{nombre}/{precio}")
    public ResponseEntity<PrecioProducto> modificarPrecioProducto(@PathVariable String comercio, @PathVariable String nombre, @PathVariable double precio) {
        Optional<Producto> productoAux = productoService.findByNombre(nombre);
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(comercio);

        Optional<PrecioProducto> precioProductoExistente = null;

        if (productoAux.isPresent()) {
            precioProductoExistente = precioProductoService.findEspecificPrecioProducto(productoAux.get().getId(), comercioAux.get().getId());
            if (precioProductoExistente.isPresent()) {
                precioProductoExistente.get().setPrecio(precio);
                precioProductoExistente.get().setProducto(productoAux.get().getId());
                precioProductoExistente.get().setComercio(comercioAux.get().getId());
            }
        }
        return ResponseEntity.ok(precioProductoService.crearPrecioProducto(precioProductoExistente.get()));
    }

    @GetMapping("/traerProductoEspecifico/{producto}/{comercio}")
    public ResponseEntity<PrecioProductoDTO> traerProductoEspecifico(@PathVariable String producto, @PathVariable String comercio) {
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(comercio);
        Optional<Producto> productoAux = productoService.findByNombre(producto);
        Optional<PrecioProducto> precioProducto = precioProductoService.findEspecificPrecioProducto(productoAux.get().getId(), comercioAux.get().getId());
        PrecioProductoDTO ppDTO = convertidor.convertFromEntity(precioProducto.get());

        ppDTO.setProducto(productoAux.get().getNombre());
        ppDTO.setComercio(comercioAux.get().getNombre());
        return ResponseEntity.ok(ppDTO);
    }

    @GetMapping("/traerProductoEspecificoPorId/{Idproducto}")
    public ResponseEntity<PrecioProductoDTO> traerProductoEspecificoPorId(@PathVariable Long Idproducto) {
        PrecioProducto precioProducto = precioProductoService.findPrecioProductoById(Idproducto);
        PrecioProductoDTO ppDTO = convertidor.convertFromEntity(precioProducto);

        ppDTO.setProducto(traerProducto(precioProducto.getProducto()).getNombre());
        ppDTO.setComercio(traerComercio(precioProducto.getComercio()).getNombre());
        return ResponseEntity.ok(ppDTO);
    }

    @GetMapping("/traerPrecios")
    public ResponseEntity<List<PrecioProductoDTO>> traerPrecios() {
        List<PrecioProducto> pps = precioProductoService.findAllPrecioProducto();

        List<PrecioProductoDTO> ppsdto = convertidor.createFromEntities(pps);

        for(int i = 0 ; i < ppsdto.size() ; i++){
            Comercio comercioAux = traerComercio(pps.get(i).getComercio());
            Producto productoAux = traerProducto(pps.get(i).getProducto());
            ppsdto.get(i).setComercio(comercioAux.getNombre());
            ppsdto.get(i).setProducto(productoAux.getNombre());
        }

        return ResponseEntity.ok(ppsdto);
    }

    @GetMapping("/buscarIdEspecificamente/{Nproducto}/{Ncomercio}")
    public ResponseEntity<Long> findEspecificIDPrecioProducto(@PathVariable String Nproducto, @PathVariable String Ncomercio) {
        Optional<Producto> productoAux = productoService.findByNombre(Nproducto);
        Optional<Comercio> comercioAux = comercioService.buscarComercioPorNombre(Ncomercio);

        Optional<PrecioProducto> pp = precioProductoService.findEspecificPrecioProducto(productoAux.get().getId(), comercioAux.get().getId());

        if (pp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pp.get().getId());
    }

    private Comercio traerComercio(Object nombreOrId) {
        if (nombreOrId instanceof String) {
            return comercioService.buscarComercioPorNombre((String) nombreOrId).orElse(null);
        } else if (nombreOrId instanceof Long) {
            return comercioService.buscarComercioPorId((Long) nombreOrId);
        }
        return null;
    }

    private Producto traerProducto(Object nombreOrId) {
        if (nombreOrId instanceof String) {
            return productoService.findByNombre((String) nombreOrId).orElse(null);
        } else if (nombreOrId instanceof Long) {
            return productoService.findById((Long) nombreOrId);
        }
        return null;
    }

}
