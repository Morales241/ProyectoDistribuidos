package controladores;

import dtos.ProductoDTO;
import entidades.Producto;
import mappers.Convertidor;
import mappers.ConvertidorCategorías;
import mappers.ConvertidorProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ProductoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    Convertidor<ProductoDTO, Producto> convertidor = new ConvertidorProducto();

    ConvertidorCategorías convertidorCategorías = new ConvertidorCategorías();

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> productoDTOs = convertidor.createFromEntities(productos);
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<ProductoDTO> buscarProductoPornombre(@RequestParam String nombre) {
        Optional<Producto> producto = productoService.findByNombre(nombre);
        return  producto.map(convertidor::convertFromEntity).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorCategoria")
    public ResponseEntity<List<ProductoDTO>> buscarProductoPorCategoria(@RequestParam String categoria) {



        List<Producto> productos = productoService.findByCategoria(convertidorCategorías.identificador_Categoria_Entity(categoria));
        List<ProductoDTO> productoDTOs = productos.stream().map(convertidor::convertFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/buscarPorCoinsidencias")
    public ResponseEntity<List<ProductoDTO>> buscarCoincidenciasPorNombre(@RequestParam String cadena) {
        List<Producto> productos = productoService.findByNombreLike(cadena);
        List<ProductoDTO> productoDTOs = convertidor.createFromEntities(productos);
        return ResponseEntity.ok(productoDTOs);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ProductoDTO> guardarProducto(@RequestBody ProductoDTO productodto) {
        Optional<Producto> ProductoExistente = productoService.findByNombre(productodto.getNombre());
        if (!ProductoExistente.isPresent()) {
            return ResponseEntity.ok(convertidor.convertFromEntity(productoService.saveProducto(convertidor.convertFromDto(productodto))));
        }else{
            return ResponseEntity.ok(convertidor.convertFromEntity(ProductoExistente.get()));
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long productoid) {
        productoService.removeProducto(productoid);
        return ResponseEntity.ok().build();
    }
}
