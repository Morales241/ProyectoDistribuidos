package entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Consumidores")
public class Consumidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @ManyToMany
    @JoinTable(
            name = "WishListConsumidor"
            , joinColumns = @JoinColumn(name = "consumidor_id")
            , inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productosEnWishList;

    @OneToMany(mappedBy = "consumidor")
    private List<Resena> resenas;

    @OneToMany(mappedBy = "consumidor")
    private List<Reporte> reportes;

    public Consumidor() {
        this.productosEnWishList = new ArrayList<>();
        this.resenas  =new ArrayList<>();
        this.reportes=new ArrayList<>();
    }

    public Consumidor(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.productosEnWishList = new ArrayList<>();
        this.resenas  =new ArrayList<>();
        this.reportes=new ArrayList<>();
    }

    public Consumidor(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.productosEnWishList = new ArrayList<>();
        this.resenas  =new ArrayList<>();
        this.reportes=new ArrayList<>();
    }

    public List<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }

    public List<Resena> getResenas() {
        return resenas;
    }

    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Producto> getProductosEnWishList() {
        return productosEnWishList;
    }

    public void setProductosEnWishList(List<Producto> productosEnWishList) {
        this.productosEnWishList = productosEnWishList;
    }
}
