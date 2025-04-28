package repositorios;

import entidades.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {

    List<Revision> findByIdComercio(Long id);

    List<Revision> findByFecha(LocalDate fecha);

    List<Revision> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
