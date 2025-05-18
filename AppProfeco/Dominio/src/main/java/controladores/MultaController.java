package controladores;

import entidades.Multa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.MultaRepository;
import servicios.MultaService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<Multa>> buscarTodas(){
        return ResponseEntity.ok(multaService.findMultas());
    }

    @GetMapping("/buscarPorComercio/{comercioId}")
    public ResponseEntity<List<Multa>> buscarPorComercio(@PathVariable Long comercioId){
        return ResponseEntity.ok(multaService.findMultasByComercioId(comercioId));
    }

    @GetMapping("/buscarPorFechaEspecifica/{fecha}")
    public ResponseEntity<List<Multa>> buscarPorFechaEspecifica(@PathVariable LocalDate fecha){
        return ResponseEntity.ok(multaService.findMultasBySpecificFecha(fecha));
    }

    @GetMapping("/buscarPorPeriodo/{fechaI}/{fechaF}")
    public ResponseEntity<List<Multa>> buscarPorPeriodo(@PathVariable LocalDate fechaI, @PathVariable LocalDate fechaF){
        return ResponseEntity.ok(multaService.findMultasByPeriodo(fechaI, fechaF));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Multa> guardarMulta(@RequestBody Multa multa){
        return ResponseEntity.ok(multaService.guardarMulta(multa));
    }

    @DeleteMapping("/Eliminar/{multaId}")
    public ResponseEntity<Void> eliminarMulta(@PathVariable Long multaId){
        multaService.eliminarMulta(multaId);
        return ResponseEntity.noContent().build();
    }
}
