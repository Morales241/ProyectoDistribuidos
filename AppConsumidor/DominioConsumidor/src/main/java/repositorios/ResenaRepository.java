package repositorios;

import entidades.Consumidor;
import entidades.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByConsumidor(Consumidor consumidor);

    List<Resena> findByIdComercio(Long idComercion);

}
