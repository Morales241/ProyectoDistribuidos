package controladores;

import dtos.ComercioDTO;
import dtos.ProductoDTO;
import entidades.Comercio;
import mappers.ComercioMapper;
import mappers.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}
