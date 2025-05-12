package repositorios;

import entidades.Oferta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaRepository extends CrudRepository<Oferta, Long> {

    List<Oferta> findAll();

    List<Oferta> findByFechaFinLessThanEqual(LocalDateTime fecha);

    List<Oferta> findByFechaFinGreaterThanEqual(LocalDateTime fecha);

    List<Oferta> findByidPrecioProducto(Long id);

    List<Oferta> findByPrecioOfertaBetween(Double min, Double max);

    Optional<Oferta> findByPrecioOfertaAndDescripcionAndIdPrecioProductoAndFechaInicioAndFechaFin(Double precioOferta, String descripcion, Long idPrecioProducto, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
