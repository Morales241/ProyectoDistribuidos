package repositorios;

import entidades.Oferta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfertaRepository extends CrudRepository<Oferta, Long> {

    List<Oferta> findAll();

    List<Oferta> findByFechaFinLessThanEqual(LocalDateTime fecha);

    List<Oferta> findByidPrecioProducto(Long id);

    List<Oferta> findByPrecioOfertaBetween(Double min, Double max);
}
