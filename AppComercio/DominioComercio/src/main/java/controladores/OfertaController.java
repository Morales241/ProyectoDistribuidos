package controladores;

import entidades.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.OfertaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @PostMapping("/guardar")
    public ResponseEntity<Oferta> create(@RequestBody Oferta oferta) {
        return ResponseEntity.ok(ofertaService.crearOferta(oferta));
    }

    @GetMapping("/buscarTodas")
    public ResponseEntity<List<Oferta>> listarOfertas() {
        return ResponseEntity.ok(ofertaService.listarOfertas());
    }

    @GetMapping("/buscarPorRangoDePrecio/{min}/{max}")
    public ResponseEntity<List<Oferta>> listarOfertasPorRangoDePrecios(@PathVariable Double min, @PathVariable Double max) {
        if (min > max) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(ofertaService.listarOfertasPorRangoDePrecios(min, max));
    }

    @GetMapping("/buscarPorProductoId/{produtoId}")
    public ResponseEntity<List<Oferta>> listarOfertaPorProdutoId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(ofertaService.listarOfertaPorProdutoId(produtoId));
    }

    @GetMapping("/buscarPorComercioId/{comercioId}")
    public ResponseEntity<List<Oferta>> listarOfertaPorComercioId(@PathVariable Long comercioId) {
        return ResponseEntity.ok(ofertaService.listarOfertaPorComercioId(comercioId));
    }

    @GetMapping("/buscarDisponibles")
    public ResponseEntity<List<Oferta>> listarOfertasDisponibles(){
        return ResponseEntity.ok(ofertaService.listarOfertasDisponibles(LocalDateTime.now()));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> removerOferta(@PathVariable Long ofertaId) {
        ofertaService.eliminarOferta(ofertaId);
        return ResponseEntity.ok().build();
    }
}
