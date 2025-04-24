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

    @PostMapping
    public ResponseEntity<Oferta> create(@RequestBody Oferta oferta) {
        return ResponseEntity.ok(ofertaService.crearOferta(oferta));
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertas() {
        return ResponseEntity.ok(ofertaService.listarOfertas());
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertasPorRangoDePrecios(@PathVariable Double min, @PathVariable Double max) {

        return ResponseEntity.ok(ofertaService.listarOfertasPorRangoDePrecios(min, max));
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertaPorProdutoId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(ofertaService.listarOfertaPorProdutoId(produtoId));
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertaPorComercioId(@PathVariable Long comercioId) {
        return ResponseEntity.ok(ofertaService.listarOfertaPorComercioId(comercioId));
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertasDisponibles(){

        return ResponseEntity.ok(ofertaService.listarOfertasDisponibles(LocalDateTime.now()));
    }

    @PostMapping
    public void removerOferta(@RequestBody Oferta oferta) {
        ofertaService.eliminarOferta(oferta);
    }
}
