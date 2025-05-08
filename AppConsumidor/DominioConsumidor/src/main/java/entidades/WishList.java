package entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WishList")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Consumidor consumidor;

    @Column(nullable = false)
    private String nombreDeMercado;

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoWishList> productos;

    public WishList() {
        productos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public List<ProductoWishList> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoWishList> productos) {
        this.productos = productos;
    }

    public String getNombre() {
        return nombreDeMercado;
    }

    public void setNombre(String nombre) {
        this.nombreDeMercado = nombre;
    }
}
