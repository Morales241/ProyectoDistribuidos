package feings;

import dtos.ReporteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient("DOMINIOCONSUMIDOR")
public interface ReporteClient {

    @GetMapping("/reportes/consumidor/{idConsumidor}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorConsumidor(@PathVariable Long idConsumidor);

    @GetMapping("/reportes/obtenerReportesPorNombreComercio/{comercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorNombreComercio(@PathVariable String comercio);

    @PostMapping("/reportes/invalidarReporte/{precioProducto}/{contenido}/{fecha}")
    public ResponseEntity<Void> invalidadReporte (@PathVariable Long precioProducto, @PathVariable String contenido,@PathVariable String fecha);

    @GetMapping("reportes/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable Long id);

    @GetMapping("/reportes/obtenerTodosLosReportes")
    public ResponseEntity<List<ReporteDTO>> obtenerTodosLosReportes();
}
