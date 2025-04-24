package controladores;

import entidades.Resena;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ResenaRepository;
import servicios.ResenaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/resena")
public class ResenaController {

    @Autowired
    private ResenaService servicio;

    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena) {
        resena.setFecha(LocalDateTime.now());
        return ResponseEntity.ok(servicio.crearResena(resena));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<Resena>> obtenerPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(servicio.obtenerPorProducto(idProducto));
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<Resena>> obtenerPorConsumidor(@PathVariable Long idConsumidor) {
        return ResponseEntity.ok(servicio.obtenerPorConsumidor(idConsumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        servicio.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }
}
