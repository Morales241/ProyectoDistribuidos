package repositorios;

import entidades.ProductoWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListProductoRepository extends JpaRepository<ProductoWishList, Long> {

    public List<ProductoWishList> findByWishListId(Long idwishList);
}
