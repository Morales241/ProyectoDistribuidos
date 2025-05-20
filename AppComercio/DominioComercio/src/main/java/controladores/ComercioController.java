package controladores;

import dtos.ComercioDTO;
import dtos.RegistroDTO;
import entidades.Comercio;
import mappers.Convertidor;
import mappers.ConvertidorComercioRegistro;
import mappers.ConvertirdorComercio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;
import servicios.EncriptamientoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/comercios")
public class ComercioController {

    private static final Logger logger = LoggerFactory.getLogger(ComercioController.class);

    Convertidor<ComercioDTO, Comercio> convertidor = new ConvertirdorComercio();
    Convertidor<RegistroDTO, Comercio> convertidorRegistro = new ConvertidorComercioRegistro();

    @Autowired
    private ComercioService comercioService;

    @Autowired
    private EncriptamientoService encriptamientoService;

    @PostMapping("/registrar")
    public ResponseEntity<RegistroDTO> registrar(@RequestBody RegistroDTO comercio) {
        logger.info("Datos recibidos: {}", comercio);
        return ResponseEntity.ok(convertidorRegistro.convertFromEntity(comercioService.crearComercio(convertidorRegistro.convertFromDto(comercio))));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarComercio(@RequestBody Long comercioId) {
        comercioService.eliminarComercio(comercioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<ComercioDTO> buscarComercioPornombre(@RequestParam String nombre) {
        Optional<Comercio> comercio = comercioService.buscarComercioPorNombre(nombre);

        return  comercio.map(convertidor::convertFromEntity).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ComercioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(convertidor.convertFromEntity(comercioService.buscarComercioPorId(id)));
    }

    @PostMapping("/inicioSesion")
    public ResponseEntity<?> inicioSesion(@RequestParam String correo, @RequestParam String contrasena) {

        Optional<Comercio> comercioAux = comercioService.buscarComercioPorCorreo(correo);

        if (comercioAux.isPresent()) {
            Comercio comercio = comercioAux.get();
            boolean passwordValido = encriptamientoService.verificarContraseña(contrasena, comercio.getContrasena());

            if (passwordValido) {
                System.out.println("inicio sesion: " + comercio.getId());
                return ResponseEntity.ok(convertidor.convertFromEntity(comercio));
            }
        }
        System.out.println("no inicio sesion");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/traerComercios")
    public ResponseEntity<List<ComercioDTO>> traerComercios() {
        return ResponseEntity.ok(convertidor.createFromEntities(comercioService.buscarComercios()));
    }

    @GetMapping("/buscarComercioIdPorNombre")
    public ResponseEntity<Long> buscarComercioIdPorNombre(@RequestParam String nombre) {
       Comercio comercio = comercioService.buscarComercioPorNombre(nombre).get();

        return  ResponseEntity.ok(comercio.getId());
    }
}
