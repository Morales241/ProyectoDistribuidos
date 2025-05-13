package feigns;

import dtos.ConsumidorDTO;
import dtos.ReporteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("DOMINIOCONSUMIDOR")
public interface ConsumidorClient {

    @GetMapping("/consumidores/traerConsumidores")
    ResponseEntity<List<ConsumidorDTO>> traerConsumidores();

    @GetMapping("/consumidor/{idConsumidor}")
    ResponseEntity<List<ReporteDTO>> obtenerPorConsumidor(@PathVariable Long idConsumidor);

    @GetMapping("/comercio/{idComercio}")
    ResponseEntity<List<ReporteDTO>> obtenerPorComercio(@PathVariable Long idComercio);

    @GetMapping("/{id}")
    ResponseEntity<ReporteDTO> obtener(@PathVariable Long id);
}
