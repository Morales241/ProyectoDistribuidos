package servicios;

import entidades.Comercio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import repositorios.ComercioResposity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ComercioService {

    @Autowired
    private ComercioResposity comercioResposity;

    public Comercio crearComercio(Comercio comercio) {
        return comercioResposity.save(comercio);
    }

    public void eliminarComercio(Long id) {
        comercioResposity.deleteById(id);
    }

}
