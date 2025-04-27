package feings;

import dtos.ReporteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("DOMINIOCONSUMIDOR")
public interface ReporteClient {

    @GetMapping("/reportes/consumidor/{idConsumidor}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorConsumidor(@PathVariable Long idConsumidor);

    @GetMapping("/reportes/comercio/{idComercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorComercio(@PathVariable Long idComercio);

    @GetMapping("reportes/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable Long id);
}
