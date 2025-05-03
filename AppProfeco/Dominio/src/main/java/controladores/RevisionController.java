package controladores;
import convertidores.Convertidor;
import convertidores.ConvertidorRevision;
import dtos.RevisionDTO;
import entidades.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.RevisionService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/reviciones")
public class RevisionController {

    @Autowired
    private RevisionService service;

    private Convertidor<RevisionDTO, Revision> convertidor = new ConvertidorRevision();

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<RevisionDTO>> buscarTodas(){
        return ResponseEntity.ok(convertidor.createFromEntities(service.findRevisiones()));
    }

    @GetMapping("/buscarPorComercio/{comercioId}")
    public ResponseEntity<List<RevisionDTO>> buscarPorComercio(@PathVariable Long comercioId){
        return ResponseEntity.ok(convertidor.createFromEntities(service.findRevisionesByComercioId(comercioId)));
    }

    @GetMapping("/buscarPorFechaEspecifica/{fecha}")
    public ResponseEntity<List<RevisionDTO>> buscarPorFechaEspecifica(@PathVariable LocalDate fecha){
        return ResponseEntity.ok(convertidor.createFromEntities(service.findRevisionesBySpecificFecha(fecha)));
    }

    @GetMapping("/buscarPorPeriodo/{fechaI}/{fechaF}")
    public ResponseEntity<List<RevisionDTO>> buscarPorPeriodo(@PathVariable LocalDate fechaI, @PathVariable LocalDate fechaF){
        return ResponseEntity.ok(convertidor.createFromEntities(service.findRevisionesByPeriodo(fechaI, fechaF)));
    }

    @PostMapping("/guardar")
    public ResponseEntity<RevisionDTO> guardarRevision(@RequestBody Revision revision){
        return ResponseEntity.ok(convertidor.convertFromEntity(service.guardarRevision(revision)));
    }

    @DeleteMapping("/Eliminar/{multaId}")
    public ResponseEntity<Void> eliminarRevision(@PathVariable Long multaId){
        service.eliminarRevision(multaId);
        return ResponseEntity.noContent().build();
    }
}
