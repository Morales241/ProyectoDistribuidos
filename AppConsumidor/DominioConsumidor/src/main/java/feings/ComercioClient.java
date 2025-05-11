package feings;

import dtos.ComercioDTO;
import dtos.PrecioProductoDTO;
import dtos.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/comercios/{id}")
    ResponseEntity<ComercioDTO> obtener(@PathVariable Long id);

    @GetMapping("/traerProductoEspecificoPorId/{Idproducto}")
    public ResponseEntity<PrecioProductoDTO> traerProductoEspecificoPorId(@PathVariable Long Idproducto);

    @GetMapping("/traerProductoEspecifico/{producto}/{comercio}")
    public ResponseEntity<Long> traerProductoEspecifico(@PathVariable String producto, @PathVariable String comercio);

    @GetMapping("/traerPrecios")
    public ResponseEntity<List<PrecioProductoDTO>> traerPrecios();
}
