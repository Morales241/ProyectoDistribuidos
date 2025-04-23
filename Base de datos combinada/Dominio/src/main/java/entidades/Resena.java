package entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Resenas")
public class Resena {
    @Id
    private Long id;

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private String NombreProducto;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
