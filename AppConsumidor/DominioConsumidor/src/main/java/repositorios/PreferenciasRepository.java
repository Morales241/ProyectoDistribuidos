package repositorios;

import entidades.Consumidor;
import entidades.Preferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferenciasRepository extends JpaRepository<Preferencias, Long> {

    List<Preferencias> findByConsumidorId(Long consumidorId);

    Optional<Preferencias> findByConsumidorAndIdPrecioProducto(Consumidor consumidor, Long idPrecioProducto);
}
