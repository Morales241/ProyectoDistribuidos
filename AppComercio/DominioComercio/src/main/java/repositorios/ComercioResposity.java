package repositorios;

import entidades.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComercioResposity extends JpaRepository<Comercio, Long> {

    Optional<Comercio> findByNombre(String nombre);

}
