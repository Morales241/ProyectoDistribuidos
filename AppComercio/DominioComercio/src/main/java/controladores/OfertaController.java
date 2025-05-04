package controladores;

import dtos.CarritoDTO;
import dtos.OfertaDTO;
import entidades.Oferta;
import feigns.ConsumidorClient;
import mappers.OfertaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ComercioService;
import servicios.NotificacionService;
import servicios.OfertaService;
import servicios.ProductoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private ComercioService comercioService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private ConsumidorClient clienteConsumidor;

    @PostMapping("/guardar")
    public ResponseEntity<OfertaDTO> create(@RequestBody OfertaDTO ofertaDTO) {
        Oferta oferta = ofertaService.crearOferta(OfertaMapper.toEntity(ofertaDTO));
        List<CarritoDTO> wishList = clienteConsumidor.obtenerWishlistPorProducto(oferta.getProducto()).getBody();
        List<String> ids = wishList.stream()
                .map(carrito -> carrito.getConsumidor().getId().toString())
                .toList();
        notificacionService.enviarNotificacion(ids, ofertaDTO);
        return ResponseEntity.ok(OfertaMapper.toDTO(oferta));
    }

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<OfertaDTO>> listarOfertas() {
        List<Oferta> ofertas = ofertaService.listarOfertas();
        List<OfertaDTO> ofertasDTO = ofertas.stream().map(oferta -> OfertaMapper.toDTO(oferta)).collect(Collectors.toList());

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarPorRangoDePrecio/{min}/{max}")
    public ResponseEntity<List<OfertaDTO>> listarOfertasPorRangoDePrecios(@PathVariable Double min, @PathVariable Double max) {
        if (min > max) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Oferta> ofertas = ofertaService.listarOfertasPorRangoDePrecios(min, max);
        List<OfertaDTO> ofertasDTO = ofertas.stream().map(oferta -> OfertaMapper.toDTO(oferta)).collect(Collectors.toList());

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarPorProductoId/{produtoId}")
    public ResponseEntity<List<OfertaDTO>> listarOfertaPorProdutoId(@PathVariable Long produtoId) {
        List<Oferta> ofertas = ofertaService.listarOfertaPorProdutoId(produtoId);
        List<OfertaDTO> ofertasDTO = ofertas.stream().map(oferta -> OfertaMapper.toDTO(oferta)).collect(Collectors.toList());

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarPorComercioId/{comercioId}")
    public ResponseEntity<List<OfertaDTO>> listarOfertaPorComercioId(@PathVariable Long comercioId) {
        List<Oferta> ofertas = ofertaService.listarOfertaPorComercioId(comercioId);
        List<OfertaDTO> ofertasDTO = ofertas.stream().map(oferta -> OfertaMapper.toDTO(oferta)).collect(Collectors.toList());

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarDisponibles")
    public ResponseEntity<List<OfertaDTO>> listarOfertasDisponibles(){
        List<Oferta> ofertas = ofertaService.listarOfertasDisponibles(LocalDateTime.now());
        List<OfertaDTO> ofertasDTO = ofertas.stream().map(oferta -> OfertaMapper.toDTO(oferta)).collect(Collectors.toList());

        return ResponseEntity.ok(ofertasDTO);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removerOferta(@PathVariable Long ofertaId) {
        ofertaService.eliminarOferta(ofertaId);
        return ResponseEntity.ok().build();
    }
}
