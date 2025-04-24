package controladores;

import entidades.Preferencias;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.PreferenciasRepository;

import java.util.List;

@RestController
@RequestMapping("/preferencias")
@RequiredArgsConstructor
public class PreferenciasController {

    private final PreferenciasRepository preferenciasRepository;

    @PostMapping
    public ResponseEntity<Preferencias> agregarPreferencia(@RequestBody Preferencias preferencia) {
        if (preferenciasRepository.existsByConsumidorIdAndIdComercio(
                preferencia.getConsumidor().getId(),
                preferencia.getIdComercio())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(preferenciasRepository.save(preferencia));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Preferencias>> obtenerPreferencias(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(preferenciasRepository.findByConsumidorId(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPreferencia(@PathVariable Long id) {
        preferenciasRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
