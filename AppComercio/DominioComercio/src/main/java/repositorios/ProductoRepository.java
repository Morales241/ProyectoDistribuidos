package repositorios;

import entidades.Comercio;
import entidades.Producto;
import enums.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByNombreLike(String nombre);

    List<Producto> findByCategoria(CategoriaProducto categoria);

}
