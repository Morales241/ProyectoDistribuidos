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
import java.util.Optional;

@FeignClient("COMERCIODOMINIO")
public interface ComercioClient {

    @GetMapping("/productos/buscarPorNombre")
    public ResponseEntity<ProductoDTO> buscarProductoPornombre(@RequestParam String nombre);

    @GetMapping("/precioProductos/buscarPorProductoId/{productoid}")
    public ResponseEntity<List<PrecioProductoDTO>> findByProductoId(@PathVariable Long productoid);

    @GetMapping("/comercios/{id}")
    public ResponseEntity<ComercioDTO> obtener(@PathVariable Long id);
}
