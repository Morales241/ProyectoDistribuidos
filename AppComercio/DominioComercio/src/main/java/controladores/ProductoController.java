package controladores;

import dtos.ProductoDTO;
import entidades.Producto;
import jakarta.websocket.server.PathParam;
import mappers.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> productoDTOs = productos.stream().map(ProductoMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<ProductoDTO> buscarProductoPornombre(@RequestParam String nombre) {
        Optional<Producto> producto = productoService.findByNombre(nombre);

        return  producto.map(ProductoMapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorCategoria")
    public ResponseEntity<List<ProductoDTO>> buscarProductoPorCategoria(@RequestParam String categoria) {
        List<Producto> productos = productoService.findByCategoria(categoria);
        List<ProductoDTO> productoDTOs = productos.stream().map(ProductoMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/buscarPorConisidencias")
    public ResponseEntity<List<ProductoDTO>> buscarCoincidenciasPorNombre(@RequestParam String cadena) {
        List<Producto> productos = productoService.findByNombreLike(cadena);
        List<ProductoDTO> productoDTOs = productos.stream().map(ProductoMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(productoDTOs);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ProductoDTO> guardarProducto(@RequestBody ProductoDTO productodto) {
        Producto producto = ProductoMapper.toEntity(productodto);
        return ResponseEntity.ok(ProductoMapper.toDTO(productoService.saveProducto(producto)));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long productoid) {
        productoService.removeProducto(productoid);
        return ResponseEntity.ok().build();
    }
}
