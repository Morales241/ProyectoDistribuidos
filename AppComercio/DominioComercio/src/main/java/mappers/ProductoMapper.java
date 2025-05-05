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

    public static CategoriaProducto identificador_Categoria_Entity(String categoria){
        CategoriaProducto categoriaProducto;
        if(categoria.isEmpty()){
            return null;
        }
        switch (categoria){
            case "Frutas y Verduras":
                return CategoriaProducto.Frutas_y_Verduras;

            case "Lácteos":
                return CategoriaProducto.Lacteos;

            case "Carnes":
                return CategoriaProducto.Carnes;

            case "Panadería":
                return CategoriaProducto.Panaderia;

            case "Bebidas":
                return CategoriaProducto.Bebidas;

            case "Snacks":
                return CategoriaProducto.Snacks;

            case "Limpieza":
                return CategoriaProducto.Limpieza;

            case "Higiene Personal":
                return CategoriaProducto.Higiene_Personas;

            case "Mascotas":
                return CategoriaProducto.Mascotas;

            case "ElectrónicaFrutas y Verduras":
                return CategoriaProducto.Electronica;

            default:
                return null;
        }
    }

    public static String identificador_Categoria_DTO(CategoriaProducto categoria){

        switch (categoria){
            case CategoriaProducto.Frutas_y_Verduras:
                return "Frutas y VerdurasLácteos";

            case CategoriaProducto.Lacteos:
                return "Lácteos";

            case CategoriaProducto.Carnes:
                return "Carnes";

            case CategoriaProducto.Panaderia:
                return "Panadería";

            case CategoriaProducto.Bebidas:
                return "Bebidas";

            case CategoriaProducto.Snacks:
                return "Snacks";

            case CategoriaProducto.Limpieza:
                return "Limpieza";

            case CategoriaProducto.Higiene_Personas:
                return "Higiene Personal";

            case CategoriaProducto.Mascotas:
                return "Mascotas";

            case CategoriaProducto.Electronica:
                return "Electrónica";

            default:
                return null;
        }
    }
}
