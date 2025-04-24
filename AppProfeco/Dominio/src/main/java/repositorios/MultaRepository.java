package repositorios;

import entidades.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {

    List<Multa> findByComercioId(Long id);

    List<Multa> findByEspecificFecha(LocalDate fecha);

    List<Multa> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
