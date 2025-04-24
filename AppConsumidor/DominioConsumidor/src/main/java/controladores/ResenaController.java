package controladores;

import entidades.Resena;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ResenaRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/resena")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaRepository resenaRepository;

    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena) {
        resena.setFecha(LocalDateTime.now());
        return ResponseEntity.ok(resenaRepository.save(resena));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<Resena>> obtenerPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(resenaRepository.findByProductoEnResena(idProducto));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Resena>> obtenerPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(resenaRepository.findByConsumidorId(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
