package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorConsumidor;
import dtos.ConsumidorDTO;
import entidades.Consumidor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ConsumidorRepository;
import servicios.ConsumidorService;

import java.time.LocalDate;

@RestController
@RequestMapping("/consumidores")
public class ConsumidorController {

    @Autowired
    private ConsumidorService servicio;

    private Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    @PostMapping
    public ResponseEntity<Consumidor> registrar(@RequestBody Consumidor consumidor) {
        consumidor.setFechaRegistro(LocalDate.now());
        return ResponseEntity.ok(servicio.registrar(consumidor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumidorDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(convertidorConsumidor.convertFromEntity(servicio.obtener(id).get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
