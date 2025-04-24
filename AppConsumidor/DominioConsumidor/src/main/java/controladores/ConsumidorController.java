package controladores;

import entidades.Consumidor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ConsumidorRepository;

import java.time.LocalDate;

@RestController
@RequestMapping("/consumidor")
@RequiredArgsConstructor
public class ConsumidorController {

    private final ConsumidorRepository consumidorRepository;

    @PostMapping
    public ResponseEntity<Consumidor> registrar(@RequestBody Consumidor consumidor) {
        consumidor.setFechaRegistro(LocalDate.now());
        return ResponseEntity.ok(consumidorRepository.save(consumidor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumidor> obtener(@PathVariable Long id) {
        return consumidorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        consumidorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
