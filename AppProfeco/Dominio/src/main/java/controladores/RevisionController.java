package controladores;
import entidades.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.RevisionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reviciones")
public class RevisionController {

    @Autowired
    private RevisionService service;

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<Revision>> buscarTodas(){
        return ResponseEntity.ok(service.findRevisiones());
    }

    @GetMapping("/buscarPorComercio/{comercioId}")
    public ResponseEntity<List<Revision>> buscarPorComercio(@PathVariable Long comercioId){
        return ResponseEntity.ok(service.findRevisionesByComercioId(comercioId));
    }

    @GetMapping("/buscarPorFechaEspecifica/{fecha}")
    public ResponseEntity<List<Revision>> buscarPorFechaEspecifica(@PathVariable LocalDate fecha){
        return ResponseEntity.ok(service.findRevisionesBySpecificFecha(fecha));
    }

    @GetMapping("/buscarPorPeriodo/{fechaI}/{fechaF}")
    public ResponseEntity<List<Revision>> buscarPorPeriodo(@PathVariable LocalDate fechaI, @PathVariable LocalDate fechaF){
        return ResponseEntity.ok(service.findRevisionesByPeriodo(fechaI, fechaF));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Revision> guardarRevision(@RequestBody Revision revision){
        return ResponseEntity.ok(service.guardarRevision(revision));
    }

    @DeleteMapping("/Eliminar/{multaId}")
    public ResponseEntity<Void> eliminarRevision(@PathVariable Long multaId){
        service.eliminarRevision(multaId);
        return ResponseEntity.noContent().build();
    }
}
