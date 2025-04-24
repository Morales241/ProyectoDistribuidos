package servicios;

import entidades.PrecioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.PrecioProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PrecioProductoService {

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    public List<PrecioProducto> findByComercioId(Long comercioId) {
        return precioProductoRepository.findByComercio(comercioId);
    }

    public List<PrecioProducto> findByProductoId(Long productoId) {
        return precioProductoRepository.findByProducto(productoId);
    }

    public Optional<PrecioProducto> findEspecificPrecioProducto(Long productoId, Long comercioId) {
        return precioProductoRepository.findByProductoAndComercio(productoId, comercioId);
    }

    public void crearPrecioProducto(PrecioProducto precioProducto) {
        precioProductoRepository.save(precioProducto);
    }

    public void eliminarPrecioProducto(PrecioProducto precioProducto) {
        precioProductoRepository.delete(precioProducto);
    }
}
