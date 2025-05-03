package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorReporte;
import dtos.ConsumidorDTO;
import dtos.ReporteDTO;
import entidades.Reporte;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ReporteRepository;
import servicios.ReporteService;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    Convertidor<ReporteDTO, Reporte> convertidor = new ConvertidorReporte();

    @Autowired
    private ReporteService servicio;

    @PostMapping
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte reporte) {
        return ResponseEntity.ok(servicio.crearReporte(reporte));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Reporte>> obtenerPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerPorConsumidor(idConsumidor));
    }

    @GetMapping("/comercio/{idComercio}")
    public ResponseEntity<List<Reporte>> obtenerPorComercio(@PathVariable Long idComercio) {
        return ResponseEntity.ok(servicio.obtenerPorComercio(idComercio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(convertidor.convertFromEntity(servicio.obtener(id).get()));
    }

}
