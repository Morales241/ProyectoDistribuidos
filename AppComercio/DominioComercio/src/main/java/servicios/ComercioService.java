package servicios;

import entidades.Comercio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ComercioRepository;

import java.util.Optional;

@Service
public class ComercioService {

    @Autowired
    private ComercioRepository comercioRepository;

    public Comercio crearComercio(Comercio comercio) {
        return this.comercioRepository.save(comercio);
    }

    public Comercio buscarComercioPorId(Long id) {
        return comercioRepository.findById(id).orElse(null);
    }

    public void eliminarComercio(Long id) {
        comercioRepository.deleteById(id);
    }

    public Optional<Comercio> buscarComercioPorNombre(String nombre) {
        return comercioRepository.findByNombre(nombre);
    }

    public Optional<Comercio> iniciarSesion(String email, String password) {
        return comercioRepository.iniciarSesion(email, password);
    }
}
