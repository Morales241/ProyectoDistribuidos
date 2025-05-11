package mappers;

import enums.CategoriaProducto;

public class ConvertidorCategorías {

    public ConvertidorCategorías(){}

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
