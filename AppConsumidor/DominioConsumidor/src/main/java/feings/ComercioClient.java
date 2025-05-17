package feings;

import dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("DOMINIOCOMERCIO")
public interface ComercioClient {

    @GetMapping("/comercios/buscarPorNombre")
    ResponseEntity<ComercioDTO> buscarComercioPornombre(@RequestParam String nombre);

    @GetMapping("/productos/buscarPorNombre")
    ResponseEntity<ProductoDTO> buscarProductoPornombre(@RequestParam String nombre);

    @GetMapping("/productos/buscarTodos")
    public ResponseEntity<List<ProductoDTO>> listarProductos();

    @GetMapping("/precioProductos/buscarPorProductoId/{productoid}")
    ResponseEntity<List<PrecioProductoDTO>> findByProductoId(@PathVariable Long productoid);

    @GetMapping("/comercios/obtener/{id}")
    ResponseEntity<ComercioDTO> obtener(@PathVariable Long id);

    @GetMapping("/precioProductos/traerProductoEspecificoPorId/{Idproducto}")
    public ResponseEntity<PrecioProductoDTO> traerProductoEspecificoPorId(@PathVariable Long Idproducto);

    @GetMapping("/precioProductos/buscarIdEspecificamente/{producto}/{comercio}")
    public ResponseEntity<Long> findEspecificIDPrecioProducto(@PathVariable String producto, @PathVariable String comercio);

    @GetMapping("/precioProductos/traerPrecios")
    public ResponseEntity<List<PrecioProductoDTO>> traerPrecios();

    @PostMapping("/ofertas/cambiarPrecio")
    public ResponseEntity<OfertaDTO> TerminarOferta(@RequestBody OfertaDTO ofertaDTO);

    @GetMapping("/ofertas/Ofertasvigentes")
    public ResponseEntity<List<OfertaDTO>> obtenerOfertasVigentes();

    @GetMapping("/comercios/traerComercios")
    public ResponseEntity<List<ComercioDTO>> traerComercios();

    @GetMapping("/comercios/buscarComercioIdPorNombre")
    public ResponseEntity<Long> buscarComercioIdPorNombre(@RequestParam String nombre);

    @GetMapping("/precioProductos/buscarComercioPorNombre/{comercio}")
    public ResponseEntity<List<PrecioProductoDTO>> findByComercioId(@PathVariable String comercio);
}
