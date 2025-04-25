package mappers;

import dtos.ProductoDTO;
import entidades.Producto;

public class ProductoMapper {

    public static Producto toEntity(ProductoDTO dto) {

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(dto.getCategoria());
        return producto;
    }

    public static ProductoDTO toDTO(Producto producto) {

        ProductoDTO productodto = new ProductoDTO();
        productodto.setId(producto.getId());
        productodto.setNombre(producto.getNombre());
        productodto.setDescripcion(producto.getDescripcion());
        productodto.setCategoria(producto.getCategoria());
        return productodto;
    }
}
