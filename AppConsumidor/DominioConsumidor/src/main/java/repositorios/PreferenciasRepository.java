package repositorios;

import entidades.Preferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenciasRepository extends JpaRepository<Preferencias, Long> {

    List<Preferencias> findByConsumidorId(Long consumidorId);

    boolean existsByConsumidorIdAndIdComercio(Long consumidorId, Long idComercio);

}
