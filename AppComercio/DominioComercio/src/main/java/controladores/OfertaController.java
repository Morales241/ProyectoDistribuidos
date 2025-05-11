package controladores;

import dtos.CarritoDTO;
import dtos.OfertaDTO;
import dtos.PrecioProductoDTO;
import entidades.Comercio;
import entidades.Oferta;
import entidades.PrecioProducto;
import entidades.Producto;
import feigns.ConsumidorClient;
import mappers.Convertidor;
import mappers.ConvertidorOferta;
import mappers.ConvertidorPrecioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/ofertas")
public class OfertaController {

    Convertidor<OfertaDTO, Oferta> convertidor = new ConvertidorOferta();
    Convertidor<PrecioProductoDTO, PrecioProducto> convertidorPP = new ConvertidorPrecioProducto();

    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private ComercioService comercioService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PrecioProductoService precioproductoService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private ConsumidorClient clienteConsumidor;

    @PostMapping("/guardar")
    public ResponseEntity<OfertaDTO> create(@RequestBody OfertaDTO ofertaDTO) {
        Oferta oferta = convertidor.convertFromDto(ofertaDTO);

        oferta.setIdPrecioProducto(traerPrecioProducto(ofertaDTO.getProducto(), ofertaDTO.getComercio()).getId());

        ofertaService.crearOferta(oferta);

        List<CarritoDTO> carritoAux = clienteConsumidor.obtenerCarritosPorProducto(oferta.getIdPrecioProducto()).getBody();
        List<String> ids = carritoAux.stream()
                .map(carrito -> carrito.getConsumidor().getId().toString())
                .toList();
        notificacionService.enviarNotificacion(ids, ofertaDTO);
        return ResponseEntity.ok(convertidor.convertFromEntity(oferta));
    }

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<OfertaDTO>> listarOfertas() {
        List<Oferta> ofertas = ofertaService.listarOfertas();
        List<OfertaDTO> ofertasDTO = traerInfoDeOfertas(ofertas);

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarPorRangoDePrecio/{min}/{max}")
    public ResponseEntity<List<OfertaDTO>> listarOfertasPorRangoDePrecios(@PathVariable Double min, @PathVariable Double max) {
        if (min > max) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Oferta> ofertas = ofertaService.listarOfertasPorRangoDePrecios(min, max);
        List<OfertaDTO> ofertasDTO = traerInfoDeOfertas(ofertas);

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarPorProductoId/{produtoId}")
    public ResponseEntity<List<OfertaDTO>> listarOfertaPorProdutoId(@PathVariable Long produtoId) {
        List<Oferta> ofertas = ofertaService.listarOfertaPorProductoId(produtoId);
        List<OfertaDTO> ofertasDTO = traerInfoDeOfertas(ofertas);

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarPorComercioId/{comercioId}")
    public ResponseEntity<List<OfertaDTO>> listarOfertaPorComercioId(@PathVariable Long comercioId) {

        List<Oferta> ofertas = ofertaService.listarOfertaPorComercioId(comercioId);
        List<OfertaDTO> ofertasDTO = traerInfoDeOfertas(ofertas);

        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/buscarDisponibles")
    public ResponseEntity<List<OfertaDTO>> listarOfertasDisponibles() {
        List<Oferta> ofertas = ofertaService.listarOfertasDisponibles(LocalDateTime.now());
        List<OfertaDTO> ofertasDTO = traerInfoDeOfertas(ofertas);

        return ResponseEntity.ok(ofertasDTO);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removerOferta(@PathVariable Long ofertaId) {
        ofertaService.eliminarOferta(ofertaId);
        return ResponseEntity.ok().build();
    }

    public PrecioProducto traerPrecioProducto(Object NombreOrIdProducto, Object NombreOrIdComercio) {
        if (NombreOrIdComercio == null && NombreOrIdProducto instanceof Long) {
            return precioproductoService.findPrecioProductoById((Long) NombreOrIdProducto);
        }
        return precioproductoService.findEspecificPrecioProducto(traerProducto(NombreOrIdProducto).getId(), traerComercio(NombreOrIdComercio).getId()).get();

    }

    private Comercio traerComercio(Object nombreOrId) {
        if (nombreOrId instanceof String) {
            return comercioService.buscarComercioPorNombre((String) nombreOrId).orElse(null);
        } else if (nombreOrId instanceof Long) {
            return comercioService.buscarComercioPorId((Long) nombreOrId);
        }
        return null;
    }

    private Producto traerProducto(Object nombreOrId) {
        if (nombreOrId instanceof String) {
            return productoService.findByNombre((String) nombreOrId).orElse(null);
        } else if (nombreOrId instanceof Long) {
            return productoService.findById((Long) nombreOrId);
        }
        return null;
    }

    private List<OfertaDTO> traerInfoDeOfertas(List<Oferta> ofertas) {
        List<OfertaDTO> ofertasDTO = convertidor.createFromEntities(ofertas);

        for(int i = 0 ; i < ofertasDTO.size() ; i++){
            PrecioProducto precioProducto = traerPrecioProducto(ofertas.get(i).getIdPrecioProducto(), null);

            ofertasDTO.get(i).setComercio(traerComercio(precioProducto.getProducto()).getNombre());
            ofertasDTO.get(i).setProducto(traerProducto(precioProducto.getProducto()).getNombre());
        }
        return ofertasDTO;
    }
}
