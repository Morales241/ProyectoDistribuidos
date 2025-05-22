package controladores;

import dtos.ComercioDTO;
import dtos.ReporteDTO;
import feings.ComercioClient;
import feings.ReporteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/reportesProfeco")
public class ReporteController {

    @Autowired
    private ComercioClient clienteComercio;

    @Autowired
    private ReporteClient reporteComercio;

    @PostMapping("invalidarReporte/{nProducto}/{nComercio}/{contenido}/{fecha}")
    public ResponseEntity<Void> invalidadReporte(@PathVariable String nProducto, @PathVariable String nComercio, @PathVariable String contenido, @PathVariable String fecha){

        Long idPP = clienteComercio.findEspecificIDPrecioProducto(nProducto, nComercio).getBody();

        reporteComercio.invalidadReporte(idPP, contenido, fecha);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/buscarReportesPorNombreComercio/{comercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorNombreComercio(@PathVariable String comercio) {
         return ResponseEntity.ok(reporteComercio.obtenerPorNombreComercio(comercio).getBody());
    }

    @GetMapping("/obtenerTodosLosReportes")
    public ResponseEntity<List<ReporteDTO>> obtenerTodosLosReportes(){
        return ResponseEntity.ok(reporteComercio.obtenerTodosLosReportes().getBody());
    }

}
