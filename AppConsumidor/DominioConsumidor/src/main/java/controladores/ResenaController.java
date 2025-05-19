package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorResena;
import dtos.ResenaDTO;
import entidades.Consumidor;
import entidades.Resena;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ConsumidorService;
import servicios.ResenaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/resenas")
public class ResenaController {

    Convertidor<ResenaDTO, Resena> convertidor = new ConvertidorResena();

    @Autowired
    private ResenaService servicio;

    @Autowired
    private ConsumidorService consumidorService;

    @Autowired
    private ComercioClient comercioClient;

    @PostMapping("/guardarResena")
    public ResponseEntity<ResenaDTO> crearResena(@RequestBody ResenaDTO resenaP) {
        resenaP.setFecha(LocalDateTime.now());
        Resena resena = convertidor.convertFromDto(resenaP);
        Consumidor consumidor = consumidorService.obtener(resenaP.getIdconsumidor()).get();
        Long idComercio = comercioClient.buscarComercioIdPorNombre(resenaP.getComercio()).getBody();
        resena.setConsumidor(consumidor);
        resena.setIdComercio(idComercio);
        resenaP.setIdconsumidor(consumidor.getId());

        servicio.crearResena(resena);

        return ResponseEntity.ok(resenaP);
    }

    @GetMapping("/consumidor/{idConsumidor}")
    public ResponseEntity<List<ResenaDTO>> obtenerPorConsumidor(@PathVariable Long idConsumidor) {
        Consumidor consumidor = consumidorService.obtener(idConsumidor).get();
        List<Resena> resenas = servicio.obtenerPorConsumidor(consumidor);
        List<ResenaDTO> resenaDTOS = new ArrayList<>();
        resenaDTOS = traerInfo(resenas);

        return ResponseEntity.ok(resenaDTOS);
    }

    @GetMapping("/consumidor/{comercio}")
    public ResponseEntity<List<ResenaDTO>> obtenerPorComercio(@PathVariable String comercio) {
        Long idComercio = comercioClient.buscarComercioIdPorNombre(comercio).getBody();
        List<Resena> resenas = servicio.obtenerPorComercio(idComercio);
        List<ResenaDTO> resenaDTOS = new ArrayList<>();
        resenaDTOS = traerInfo(resenas);

        return ResponseEntity.ok(resenaDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        servicio.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }

    private List<ResenaDTO> traerInfo(List<Resena> resenas){

        List<ResenaDTO> resenaDTOS = new ArrayList<>();

        resenaDTOS = convertidor.createFromEntities(resenas);

        for (int i = 0; i < resenas.size(); i++) {
            resenaDTOS.get(i).setComercio(comercioClient.obtener(resenas.get(i).getIdComercio()).getBody().getNombre());
            resenaDTOS.get(i).setIdconsumidor(resenas.get(i).getConsumidor().getId());
        }

        return resenaDTOS;
    }
}
