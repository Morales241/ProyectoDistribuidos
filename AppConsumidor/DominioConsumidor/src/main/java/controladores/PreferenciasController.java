package controladores;

import entidades.Preferencias;
import excepciones.ConsumidorServiciosException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.PreferenciasRepository;
import servicios.PreferenciasService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/preferencias")
public class PreferenciasController {

    private PreferenciasService servicio;

    @PostMapping
    public ResponseEntity<Preferencias> agregarPreferencia(@RequestBody Preferencias preferencia) {
        try {
            Preferencias preferencias = servicio.agregarPreferencia(preferencia);
            return ResponseEntity.ok(preferencias);
        } catch (ConsumidorServiciosException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Preferencias>> obtenerPreferencias(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerPreferencias(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPreferencia(@PathVariable Long id) {
        servicio.eliminarPreferencia(id);
        return ResponseEntity.noContent().build();
    }
}
