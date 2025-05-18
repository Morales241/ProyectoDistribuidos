package controladores;

import dtos.ComercioDTO;
import dtos.ReporteDTO;
import feings.ComercioClient;
import feings.ReporteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/reportesProfeco")
public class ReporteController {

    @Autowired
    private ComercioClient clienteComercio;

    @Autowired
    private ReporteClient reporteComercio;

    @PostMapping("invalidarReporte/{precioProducto}/{contenido}/{fecha}")
    public ResponseEntity<Void> invalidadReporte(@PathVariable Long precioProducto, @PathVariable String contenido, @PathVariable LocalDateTime fecha){
        clienteComercio.invalidadReporte(precioProducto, contenido, fecha);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscarReportesPorNombreComercio/{comercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorNombreComercio(@PathVariable String comercio) {
         return ResponseEntity.ok(reporteComercio.obtenerPorNombreComercio(comercio).getBody());
    }

}
