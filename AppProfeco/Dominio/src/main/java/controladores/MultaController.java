package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorMulta;
import dtos.ComercioDTO;
import dtos.MultaDTO;
import entidades.Multa;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.MultaRepository;
import servicios.MultaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/multas")
public class MultaController {

    Convertidor<MultaDTO, Multa> convertidor = new ConvertidorMulta();

    @Autowired
    private MultaService multaService;

    @Autowired
    private ComercioClient clienteComercio;

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<MultaDTO>> buscarTodas(){
        List<MultaDTO> multaDTOS = new ArrayList<>();
        List<Multa> multas = new ArrayList<>();
        multas = multaService.findMultas();
        multaDTOS = traerInfo(multas);
        return ResponseEntity.ok(multaDTOS);
    }

    @GetMapping("/buscarPorComercio/{comercioId}")
    public ResponseEntity<List<MultaDTO>> buscarPorComercio(@PathVariable Long comercioId){
        List<MultaDTO> multaDTOS = new ArrayList<>();
        List<Multa> multas = new ArrayList<>();
        multas = multaService.findMultasByComercioId(comercioId);
        multaDTOS = traerInfo(multas);
        return ResponseEntity.ok(multaDTOS);
    }

    @GetMapping("/buscarPorFechaEspecifica/{fecha}")
    public ResponseEntity<List<MultaDTO>> buscarPorFechaEspecifica(@PathVariable LocalDate fecha){
        List<MultaDTO> multaDTOS = new ArrayList<>();
        List<Multa> multas = new ArrayList<>();
        multas = multaService.findMultasBySpecificFecha(fecha);
        multaDTOS = traerInfo(multas);
        return ResponseEntity.ok(multaDTOS);
    }

    @GetMapping("/buscarPorPeriodo/{fechaI}/{fechaF}")
    public ResponseEntity<List<MultaDTO>> buscarPorPeriodo(@PathVariable LocalDate fechaI, @PathVariable LocalDate fechaF){
        List<MultaDTO> multasDTOS = new ArrayList<>();
        List<Multa> multas = new ArrayList<>();
        multas = multaService.findMultasByPeriodo(fechaI, fechaF);
        multasDTOS = traerInfo(multas);
        return ResponseEntity.ok(multasDTOS);
    }

    @PostMapping("/guardar")
    public ResponseEntity<MultaDTO> guardarMulta(@RequestBody MultaDTO multa){

        Multa multaEntity = new Multa();
        multaEntity = convertidor.convertFromDto(multa);

        Long idcomercio = clienteComercio.buscarComercioIdPorNombre(multa.getComercio()).getBody();

        multaEntity.setIdComercio(idcomercio);
        multaEntity.setFecha(LocalDate.now());
        multaEntity.setMotivo("Inconsistencias en el precio");

        multaService.guardarMulta(multaEntity);

        return ResponseEntity.ok(multa);
    }

    @DeleteMapping("/Eliminar/{multaId}")
    public ResponseEntity<Void> eliminarMulta(@PathVariable Long multaId){
        multaService.eliminarMulta(multaId);
        return ResponseEntity.noContent().build();
    }

    private List<MultaDTO> traerInfo(List<Multa> multas){
        List<MultaDTO> multaDTOS = new ArrayList<>();
        multaDTOS = convertidor.createFromEntities(multas);

        for(int i = 0; i < multas.size(); i++){
            ComercioDTO comercioDTO = new ComercioDTO();
            comercioDTO = clienteComercio.obtener(multas.get(i).getIdComercio()).getBody();
            multaDTOS.get(i).setComercio(comercioDTO.getNombre());
        }
        return multaDTOS;
    }
}
