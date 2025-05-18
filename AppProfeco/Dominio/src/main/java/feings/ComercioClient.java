package feings;

import dtos.ComercioDTO;
import dtos.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient("DOMINIOCOMERCIO")
public interface ComercioClient {

    @GetMapping("/comercios/{id}")
    ResponseEntity<ComercioDTO> obtener(@PathVariable Long id);

    @GetMapping("/comercios/buscarPorNombre")
    ResponseEntity<ComercioDTO> buscarComercioPornombre(@RequestParam String nombre);

    @GetMapping("/productos/buscarPorNombre")
    ResponseEntity<ProductoDTO> buscarProductoPornombre(@RequestParam String nombre);

    @GetMapping("/comercios/traerComercios")
    public ResponseEntity<List<ComercioDTO>> traerComercios();

    @GetMapping("/comercios/buscarComercioIdPorNombre")
    public ResponseEntity<Long> buscarComercioIdPorNombre(@RequestParam String nombre);

    @PostMapping("invalidarReporte/{precioProducto}/{contenido}/{fecha}")
    public ResponseEntity<Void> invalidadReporte (@PathVariable Long precioProducto, @PathVariable String contenido,@PathVariable LocalDateTime fecha);
}
