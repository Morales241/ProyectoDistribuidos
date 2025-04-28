package repositorios;

import entidades.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Long> {

    Optional<Comercio> findByNombre(String nombre);

    @Transactional
    @Query("SELECT c FROM Comercio c WHERE c.correo = :correo AND c.contrasena = :contrasena")
    Optional<Comercio> iniciarSesion(@Param("correo") String correo, @Param("contrasena") String contrasena);

}
