package mappers;

import dtos.ProductoDTO;
import entidades.Producto;
import enums.CategoriaProducto;

public class ProductoMapper {

    public static Producto toEntity(ProductoDTO dto) {
        CategoriaProducto categoria = identificador_Categoria_Entity(dto.getCategoria());


        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(categoria);
        return producto;
    }

    public static ProductoDTO toDTO(Producto producto) {

        String categoria = identificador_Categoria_DTO(producto.getCategoria());

        ProductoDTO productodto = new ProductoDTO();
        productodto.setId(producto.getId());
        productodto.setNombre(producto.getNombre());
        productodto.setDescripcion(producto.getDescripcion());
        productodto.setCategoria(categoria);
        return productodto;
    }

    protected static CategoriaProducto identificador_Categoria_Entity(String categoria){
        CategoriaProducto categoriaProducto;
        if(categoria.isEmpty()){
            return null;
        }
        switch (categoria){
            case "frutas y verduras":
                return CategoriaProducto.Frutas_y_Verduras;

            case "lacteos":
                return CategoriaProducto.Lacteos;

            case "carnes":
                return CategoriaProducto.Carnes;

            case "panaderia":
                return CategoriaProducto.Panaderia;

            case "bebidas":
                return CategoriaProducto.Bebidas;

            case "snacks":
                return CategoriaProducto.Snacks;

            case "limpieza":
                return CategoriaProducto.Limpieza;

            case "higiene personal":
                return CategoriaProducto.Higiene_Personas;

            case "mascotas":
                return CategoriaProducto.Mascotas;

            case "electronica":
                return CategoriaProducto.Electronica;

            default:
                return null;
        }
    }

    protected static String identificador_Categoria_DTO(CategoriaProducto categoria){

        switch (categoria){
            case CategoriaProducto.Frutas_y_Verduras:
                return "frutas y verduras";

            case CategoriaProducto.Lacteos:
                return "lacteos";

            case CategoriaProducto.Carnes:
                return "carnes";

            case CategoriaProducto.Panaderia:
                return "panaderia";

            case CategoriaProducto.Bebidas:
                return "bebidas";

            case CategoriaProducto.Snacks:
                return "snacks";

            case CategoriaProducto.Limpieza:
                return "limpieza";

            case CategoriaProducto.Higiene_Personas:
                return "higiene personal";

            case CategoriaProducto.Mascotas:
                return "mascotas";

            case CategoriaProducto.Electronica:
                return "electronica";

            default:
                return null;
        }
    }
}
