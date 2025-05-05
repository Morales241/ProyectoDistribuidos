package servicios;

import entidades.Producto;
import enums.CategoriaProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findByNombreLike(String nombre) {
        return productoRepository.findByNombreLike("%" + nombre + "%");
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Optional<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public List<Producto> findByCategoria(CategoriaProducto categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void removeProducto(Long productoId) {
        productoRepository.deleteById(productoId);
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
}
