package controladores;

import dtos.OfertaDTO;
import dtos.PrecioProductoDTO;
import dtos.ProductoDTO;
import entidades.Carrito;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import servicios.CarritoService;
//...

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class NotificacionController {

    private final List<OfertaDTO> ofertasRecibidas = new ArrayList<>();
    private final List<SseEmitter> emisores = new CopyOnWriteArrayList<>();


    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ComercioClient clienteComercio;

    @PostMapping("/recibirOferta")
    public ResponseEntity<Void> recibirOferta(@RequestBody OfertaDTO ofertaDTO) {
        ofertasRecibidas.add(ofertaDTO);
        for (SseEmitter emisor : emisores) {
            try {
                emisor.send(SseEmitter.event().name("nueva-oferta").data(ofertaDTO));
            } catch (Exception e) {
                emisor.completeWithError(e);
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/suscribirse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter suscribirse() {
        SseEmitter emisor = new SseEmitter(Long.MAX_VALUE);
        emisores.add(emisor);

        emisor.onCompletion(() -> emisores.remove(emisor));
        emisor.onTimeout(() -> emisores.remove(emisor));
        emisor.onError((ex) -> emisores.remove(emisor));

        return emisor;
    }

    @Scheduled(fixedRate = 60000)
    public void limpiarOfertasExpiradas() {
        LocalDateTime ahora = LocalDateTime.now();
        List<OfertaDTO> expiradas = ofertasRecibidas.stream()
                .filter(oferta -> oferta.getFechaFin().isBefore(ahora))
                .toList();

        for (OfertaDTO oferta : expiradas) {

            clienteComercio.TerminarOferta(oferta);
        }

        ofertasRecibidas.removeAll(expiradas);
    }

    @GetMapping("/verOfertas")
    public ResponseEntity<List<OfertaDTO>> verOfertas() {
        List<OfertaDTO> ofertasBaseDatos = clienteComercio.obtenerOfertasVigentes().getBody();

        List<OfertaDTO> todas = new ArrayList<>(ofertasBaseDatos);
        todas.addAll(ofertasRecibidas);

        return ResponseEntity.ok(todas);
    }

}

