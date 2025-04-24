package repositorios;

import entidades.Oferta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OfertaRepository extends CrudRepository<Oferta, Long> {

    List<Oferta> findAll();

    List<Oferta> findByFechaFinLessThanEqual(LocalDateTime fecha);

    List<Oferta> findByproductoId(Long id);

    List<Oferta> findByComercioId(Long id);

    List<Oferta> findByPrecioOfertaBetween(Double min, Double max);
}
