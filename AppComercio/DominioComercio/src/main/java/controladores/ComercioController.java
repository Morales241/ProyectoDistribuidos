package controladores;

import entidades.Comercio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;

@RestController
@RequestMapping("/comercios")
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

    @PostMapping("/guardar")
    public ResponseEntity<Comercio> crearComercio(@RequestBody Comercio comercio) {
        return ResponseEntity.ok(comercioService.crearComercio(comercio));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarComercio(@RequestBody Comercio comercio) {
        comercioService.eliminarComercio(comercio.getId());
        return ResponseEntity.ok().build();
    }
}
