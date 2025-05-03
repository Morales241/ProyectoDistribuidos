package controladores;

import dtos.ComercioDTO;
import entidades.Comercio;
import mappers.ComercioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;
import servicios.EncriptamientoService;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/comercios")
public class ComercioController {

    private static final Logger logger = LoggerFactory.getLogger(ComercioController.class);

    @Autowired
    private ComercioService comercioService;

    @Autowired
    private EncriptamientoService encriptamientoService;

    @PostMapping("/guardar")
    public ResponseEntity<ComercioDTO> crearComercio(@RequestBody ComercioDTO comercio) {

        logger.info("Datos recibidos: {}", comercio);
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

        Optional<Comercio> comercioAux = comercioService.buscarComercioPorCorreo(correo);

        if (comercioAux.isPresent()) {
            Comercio comercio = comercioAux.get();
            boolean passwordValido = encriptamientoService.verificarContraseña(contrasena, comercio.getContrasena());

            if (passwordValido) {
                System.out.println("inicio sesion: " + comercio.getId());
                return ResponseEntity.ok(ComercioMapper.toDTO(comercio));
            }
        }
        System.out.println("no inicio sesion");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
