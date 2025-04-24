package controladores;

import entidades.Consumidor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ConsumidorRepository;
import servicios.ConsumidorService;

import java.time.LocalDate;

@RestController
@RequestMapping("/consumidor")
public class ConsumidorController {

    @Autowired
    private ConsumidorService servicio;

    @PostMapping
    public ResponseEntity<Consumidor> registrar(@RequestBody Consumidor consumidor) {
        consumidor.setFechaRegistro(LocalDate.now());
        return ResponseEntity.ok(servicio.registrar(consumidor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumidor> obtener(@PathVariable Long id) {
        return servicio.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
