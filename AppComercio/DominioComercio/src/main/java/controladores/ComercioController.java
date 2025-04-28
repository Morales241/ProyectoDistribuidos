package controladores;

import dtos.ComercioDTO;
import entidades.Comercio;
import mappers.ComercioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;

import java.util.Optional;

@RestController
@RequestMapping("/comercios")
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

    @PostMapping("/guardar")
    public ResponseEntity<ComercioDTO> crearComercio(@RequestBody ComercioDTO comercio) {
        return ResponseEntity.ok(ComercioMapper.toDTO(comercioService.crearComercio(ComercioMapper.toEntity(comercio))));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarComercio(@RequestBody Long comercioId) {
        comercioService.eliminarComercio(comercioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<ComercioDTO> buscarComercioPornombre(@RequestParam String nombre) {
        Optional<Comercio> comercio = comercioService.buscarComercioPorNombre(nombre);

        return  comercio.map(ComercioMapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ComercioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ComercioMapper.toDTO(comercioService.buscarComercioPorId(id)));
    }

    @PostMapping("/inicioSesion")
    public ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String contrasena) {
        Optional<Comercio> comercio = comercioService.iniciarSesion(correo, contrasena);

        if (comercio.isPresent()) {

            Comercio comercioAux = comercio.get();

            return ResponseEntity.ok(ComercioMapper.toDTO(comercioAux));
        }else{

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Correo o contraseña incorrectos");
        }
    }
}
