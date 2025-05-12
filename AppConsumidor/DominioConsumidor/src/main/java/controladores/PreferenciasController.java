package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorConsumidor;
import dtos.ConsumidorDTO;
import dtos.PrecioProductoDTO;
import dtos.PreferenciasDTO;
import entidades.Consumidor;
import entidades.Preferencias;
import excepciones.ConsumidorServiciosException;
import feings.ComercioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositorios.PreferenciasRepository;
import servicios.ConsumidorService;
import servicios.PreferenciasService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/preferencias")
public class PreferenciasController {

    @Autowired
    private PreferenciasService servicio;

    @Autowired
    private ComercioClient comercioClient;

    @Autowired
    private ConsumidorService Consumidorservice;

    private Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    @PostMapping("/agregarPreferencia/{consumidorId}/{comercio}/{producto}")
    public ResponseEntity<PreferenciasDTO> agregarPreferencia(@PathVariable Long consumidorId, @PathVariable String comercio, @PathVariable String producto) {

        Long productoId = comercioClient.findEspecificIDPrecioProducto(producto, comercio).getBody();
        Consumidor consumidor = Consumidorservice.obtener(consumidorId).get();

        PreferenciasDTO preferenciasDTO = new PreferenciasDTO();
        preferenciasDTO.setComercio(comercio);
        preferenciasDTO.setProducto(producto);
        preferenciasDTO.setConsumidor(convertidorConsumidor.convertFromEntity(consumidor));

        Preferencias preferencia = new Preferencias();
        preferencia.setConsumidor(consumidor);
        preferencia.setIdPrecioProducto(productoId);

        servicio.agregarPreferencia(preferencia);

        return ResponseEntity.ok(preferenciasDTO);
    }

    @GetMapping("/obtenerPreferencias/{idConsumidor}")
    public ResponseEntity<List<PreferenciasDTO>> obtenerPreferencias(@PathVariable Long idConsumidor) {
        List<Preferencias> preferencias = servicio.obtenerPreferencias(idConsumidor);
        List<PreferenciasDTO> preferenciasDTO = new ArrayList<>();
        preferenciasDTO = cargarInformacion(preferencias);
        return ResponseEntity.ok(preferenciasDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPreferencia(@PathVariable Long id) {
        servicio.eliminarPreferencia(id);
        return ResponseEntity.noContent().build();
    }

    private List<PreferenciasDTO> cargarInformacion(List<Preferencias> preferencias) {
        List<PreferenciasDTO> preferenciasDTO = new ArrayList<>();

        for (Preferencias preferencia : preferencias) {
            PreferenciasDTO preferenciaDTO = new PreferenciasDTO();
            PrecioProductoDTO precioProductoDTO = comercioClient.traerProductoEspecificoPorId(preferencia.getIdPrecioProducto()).getBody();

            preferenciaDTO.setConsumidor(convertidorConsumidor.convertFromEntity(preferencia.getConsumidor()));
            preferenciaDTO.setComercio(precioProductoDTO.getComercio());
            preferenciaDTO.setProducto(precioProductoDTO.getProducto());

            preferenciasDTO.add(preferenciaDTO);
        }

        return preferenciasDTO;
    }
}
