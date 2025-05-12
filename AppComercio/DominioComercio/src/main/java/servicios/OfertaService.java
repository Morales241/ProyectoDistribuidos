package servicios;

import entidades.Oferta;
import entidades.PrecioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.OfertaRepository;
import repositorios.PrecioProductoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

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

    public List<Oferta> listarOfertaPorPPId(Long id) {
        return ofertaRepository.findByidPrecioProducto(id);
    }

    public List<Oferta> listarOfertaPorProductoId(Long id) {

        List<PrecioProducto> producto = precioProductoRepository.findByidProducto(id);

        List<Oferta> ofertas = new ArrayList<>();

        Iterator<PrecioProducto> iterator = producto.iterator();

        Iterator<Oferta> iterator2 = ofertaRepository.findAll().iterator();

        while (iterator.hasNext()) {
            PrecioProducto p = iterator.next();

            while (iterator2.hasNext()) {
                Oferta of = iterator2.next();

                if (of.getIdPrecioProducto() == p.getId()) {
                    ofertas.add(of);
                }
            }
        }

        return ofertas;
    }
    public List<Oferta> listarOfertaPorComercioId(Long id) {
        List<PrecioProducto> producto = precioProductoRepository.findByidComercio(id);

        List<Oferta> ofertas = new ArrayList<>();

        Iterator<PrecioProducto> iterator = producto.iterator();

        Iterator<Oferta> iterator2 = ofertaRepository.findAll().iterator();

        while (iterator.hasNext()) {
            PrecioProducto p = iterator.next();

            while (iterator2.hasNext()) {
                Oferta of = iterator2.next();

                if (of.getIdPrecioProducto() == p.getId()) {
                    ofertas.add(of);
                }
            }
        }

        return ofertas;
    }


    public List<Oferta> listarOfertasDisponibles(LocalDateTime fecha){
        return ofertaRepository.findByFechaFinGreaterThanEqual(fecha);
    }

    public Oferta buscarOfertaPorTodaLaInfo(Double precioOferta, String descripcion, Long idPrecioProducto, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ofertaRepository.findByPrecioOfertaAndDescripcionAndIdPrecioProductoAndFechaInicioAndFechaFin(precioOferta, descripcion, idPrecioProducto, fechaInicio, fechaFin).get();
    }
}
