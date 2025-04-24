package controladores;

import entidades.Multa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositorios.MultaRepository;
import servicios.MultaService;

import java.util.List;

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

    @GetMapping("/buscarPor")

}
