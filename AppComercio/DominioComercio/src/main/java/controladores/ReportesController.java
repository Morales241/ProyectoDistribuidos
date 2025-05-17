package controladores;

import dtos.ReporteDTO;
import feigns.ConsumidorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comercioReportes")
public class ReportesController {

    @Autowired
    private ConsumidorClient clienteConsumidor;

    @GetMapping("/buscar/{idComercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerReportes(@PathVariable Long idComercio) {
        return ResponseEntity.ok(clienteConsumidor.obtenerPorComercio(idComercio).getBody());
    }
}
