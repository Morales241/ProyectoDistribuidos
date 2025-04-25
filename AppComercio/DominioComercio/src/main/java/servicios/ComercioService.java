package servicios;

import entidades.Comercio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ComercioResposity;

import java.util.Optional;

@Service
public class ComercioService {

    @Autowired
    private ComercioResposity comercioResposity;

    public Comercio crearComercio(Comercio comercio) {
        return comercioResposity.save(comercio);
    }

    public Comercio buscarComercioPorId(Long id) {
        return comercioResposity.findById(id).orElse(null);
    }

    public void eliminarComercio(Long id) {
        comercioResposity.deleteById(id);
    }

    public Optional<Comercio> buscarComercioPorNombre(String nombre) {
        return comercioResposity.findByNombre(nombre);
    }

}
