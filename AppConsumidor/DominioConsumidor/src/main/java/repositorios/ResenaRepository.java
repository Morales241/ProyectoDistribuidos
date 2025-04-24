package repositorios;

import entidades.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByProductoEnResena(Long productoId);

    List<Resena> findByConsumidorId(Long consumidorId);

}
