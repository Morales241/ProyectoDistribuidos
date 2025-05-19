package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorReporte;
import dtos.ComercioDTO;
import dtos.ConsumidorDTO;
import dtos.PrecioProductoDTO;
import dtos.ReporteDTO;
import entidades.Consumidor;
import entidades.Reporte;
import feings.ComercioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.ReporteRepository;
import servicios.ConsumidorService;
import servicios.ReporteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    Convertidor<ReporteDTO, Reporte> convertidor = new ConvertidorReporte();

    @Autowired
    private ReporteService servicio;

    @Autowired
    private ConsumidorService servicioConsumidor;


    @Autowired
    private ComercioClient clienteComercio;


    @PostMapping("/agregar")
    public ResponseEntity<ReporteDTO> crearReporte(@RequestBody ReporteDTO dto) {

        Reporte reporte = new Reporte();
        reporte = convertidor.convertFromDto(dto);
        reporte.setFecha(LocalDateTime.now());
        Consumidor consumidor = servicioConsumidor.obtener(dto.getConsumidor()).get();
        reporte.setConsumidor(consumidor);
        Long idPP = clienteComercio.findEspecificIDPrecioProducto(dto.getProducto().getProducto(), dto.getProducto().getComercio()).getBody();
        reporte.setPrecioProducto(idPP);
        servicio.crearReporte(reporte);

        dto.setFecha(LocalDateTime.now());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorConsumidor(@PathVariable Long idConsumidor) {
        List<Reporte> reportes = new ArrayList<>();
        reportes = servicio.obtenerPorConsumidor(idConsumidor);
        List<ReporteDTO> reportesDTO = new ArrayList<>();
        reportesDTO = traerInfo(reportes);

        return ResponseEntity.ok(reportesDTO);
    }

    @GetMapping("/comercio/{idComercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorComercio(@PathVariable Long idComercio) {

        ComercioDTO comercioDTO = clienteComercio.obtener(idComercio).getBody();

        List<PrecioProductoDTO> PPs = new ArrayList<>();
        PPs = clienteComercio.findByComercioId(comercioDTO.getNombre()).getBody();

        List<Reporte> reportes = new ArrayList<>();

        for (PrecioProductoDTO pP : PPs) {
            Long ppId = clienteComercio.findEspecificIDPrecioProducto(pP.getProducto(), pP.getComercio()).getBody();
            reportes.addAll(servicio.obtenerPorPrecioProducto(ppId));
        }

        List<ReporteDTO> reportesDTO = new ArrayList<>();
        reportesDTO = traerInfo(reportes);

        return ResponseEntity.ok(reportesDTO);
    }

    @GetMapping("obtenerReportePorId/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable Long id) {
        Reporte reporte = new Reporte();
        reporte = servicio.obtener(id).get();

        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO = convertidor.convertFromEntity(reporte);
        reporteDTO.setConsumidor(reporte.getConsumidor().getId());
        PrecioProductoDTO precioProductoDTO = clienteComercio.traerProductoEspecificoPorId(reporte.getPrecioProducto()).getBody();
        reporteDTO.setProducto(precioProductoDTO);
        return ResponseEntity.ok(reporteDTO);
    }

    private List<ReporteDTO> traerInfo(List<Reporte> reportes){
        List<ReporteDTO> reportesDTO = new ArrayList<>();
        reportesDTO = convertidor.createFromEntities(reportes);

        for(int i = 0; i<reportes.size(); i++){
            PrecioProductoDTO precioProductoDTO = clienteComercio.traerProductoEspecificoPorId(reportes.get(i).getPrecioProducto()).getBody();
            reportesDTO.get(i).setConsumidor(reportes.get(i).getConsumidor().getId());
            reportesDTO.get(i).setProducto(precioProductoDTO);
            reportesDTO.get(i).setConsumidor(null);
        }

        return reportesDTO;
    }

    @PostMapping("invalidarReporte/{precioProducto}/{contenido}/{fecha}")
    private ResponseEntity<Void> invalidadReporte (@PathVariable Long precioProducto, @PathVariable String contenido,@PathVariable String fecha){

        LocalDateTime fechaAux = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSS"));

        servicio.invalidarReporte(precioProducto, contenido, fechaAux);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/obtenerReportesPorNombreComercio/{comercio}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorNombreComercio(@PathVariable String comercio) {

        ComercioDTO comercioDTO = clienteComercio.buscarComercioPornombre(comercio).getBody();

        List<PrecioProductoDTO> PPs = new ArrayList<>();
        PPs = clienteComercio.findByComercioId(comercioDTO.getNombre()).getBody();

        List<Reporte> reportes = new ArrayList<>();

        for (PrecioProductoDTO pP : PPs) {
            Long ppId = clienteComercio.findEspecificIDPrecioProducto(pP.getProducto(), pP.getComercio()).getBody();
            reportes.addAll(servicio.obtenerPorPrecioProducto(ppId));
        }

        List<ReporteDTO> reportesDTO = new ArrayList<>();
        reportesDTO = traerInfo(reportes);

        return ResponseEntity.ok(reportesDTO);
    }

    @GetMapping("/obtenerTodosLosReportes")
    public ResponseEntity<List<ReporteDTO>> obtenerTodosLosReportes() {


        List<Reporte> reportes = new ArrayList<>();
        reportes = servicio.obtenerTodosLosReportes();

        List<ReporteDTO> reportesDTO = new ArrayList<>();
        reportesDTO = traerInfo(reportes);

        return ResponseEntity.ok(reportesDTO);
    }

}
