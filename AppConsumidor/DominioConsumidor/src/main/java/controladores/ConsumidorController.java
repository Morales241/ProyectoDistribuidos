package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorConsumidor;
import dtos.ConsumidorDTO;
import entidades.Consumidor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ConsumidorRepository;
import servicios.ConsumidorService;
import servicios.EncriptamientoService;

import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/consumidores")
public class ConsumidorController {

    @Autowired
    private ConsumidorService servicio;

    @Autowired
    private EncriptamientoService encriptador;

    private Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    @PostMapping("/guardar")
    public ResponseEntity<Consumidor> registrar(@RequestBody Consumidor consumidor) {
        consumidor.setFechaRegistro(LocalDate.now());
        return ResponseEntity.ok(servicio.registrar(consumidor));
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ConsumidorDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(convertidorConsumidor.convertFromEntity(servicio.obtener(id).get()));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inicioSesion")
    public ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String contrasena) {
        Optional<Consumidor> consumidorOpt = servicio.obtenerPorCorreo(correo);

        if (consumidorOpt.isPresent()) {
            Consumidor consumidor = consumidorOpt.get();
            boolean contrasenaValida = encriptador.verificarContrasena(contrasena, consumidor.getContrasena());

            if (contrasenaValida) {
                return ResponseEntity.ok(convertidorConsumidor.convertFromEntity(consumidor));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
