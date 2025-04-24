package repositorios;

import entidades.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {

    Optional<Consumidor> findByCorreoAndContrasena(String correo, String contrasena);


}
