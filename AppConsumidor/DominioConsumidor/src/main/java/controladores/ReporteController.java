package controladores;

import entidades.Reporte;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ReporteRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteRepository reporteRepository;

    @PostMapping
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte reporte) {
        reporte.setFecha(LocalDateTime.now());
        return ResponseEntity.ok(reporteRepository.save(reporte));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Reporte>> obtenerPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(reporteRepository.findByConsumidorId(idConsumidor));
    }

    @GetMapping("/comercio/{idComercio}")
    public ResponseEntity<List<Reporte>> obtenerPorComercio(@PathVariable Long idComercio) {
        return ResponseEntity.ok(reporteRepository.findByComercioId(idComercio));
    }

}
