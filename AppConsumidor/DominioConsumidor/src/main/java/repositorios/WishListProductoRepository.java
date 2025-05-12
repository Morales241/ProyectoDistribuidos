package repositorios;

import entidades.Consumidor;
import entidades.ProductoWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListProductoRepository extends JpaRepository<ProductoWishList, Long> {

    public List<ProductoWishList> findByConsumidor(Consumidor consumidor);

    public List<ProductoWishList> findByIdComercio(Long idComercion);
}
