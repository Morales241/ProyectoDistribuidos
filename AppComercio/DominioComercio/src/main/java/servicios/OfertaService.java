package servicios;

import entidades.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import repositorios.OfertaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public Oferta crearOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public void eliminarOferta(Long ofertaId) {

        ofertaRepository.deleteById(ofertaId);
    }

    public List<Oferta> listarOfertas() {
        return ofertaRepository.findAll();
    }

    public List<Oferta> listarOfertasPorRangoDePrecios(Double min, Double max) {
        return ofertaRepository.findByPrecioOfertaBetween(min, max);
    }

    public List<Oferta> listarOfertaPorProdutoId(Long id) {
        return ofertaRepository.findByproductoId(id);
    }

    public List<Oferta> listarOfertaPorComercioId(Long id) {
        return ofertaRepository.findByComercioId(id);
    }

    public List<Oferta> listarOfertasDisponibles(LocalDateTime fecha){
        return ofertaRepository.findByFechaFinLessThanEqual(fecha);
    }
}
