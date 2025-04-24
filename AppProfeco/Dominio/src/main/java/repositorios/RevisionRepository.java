package repositorios;

import entidades.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {

    List<Revision> findByComercioId(Long id);

    List<Revision> findByEspecificFecha(LocalDate fecha);

    List<Revision> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
