package mappers;

import dtos.ProductoDTO;
import entidades.Producto;
import enums.CategoriaProducto;

public class ConvertidorProducto extends Convertidor<ProductoDTO, Producto>{

    public ConvertidorProducto() {
        super(ConvertidorProducto::convertToEntity, ConvertidorProducto::convertToDTO);
    }

    private static ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCategoria(identificador_Categoria_DTO(producto.getCategoria()));
        return productoDTO;
    }

    private static Producto convertToEntity(ProductoDTO productoDTO) {

        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setCategoria(identificador_Categoria_Entity(productoDTO.getCategoria()));
        return producto;
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
