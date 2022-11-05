package com.ar.unnoba.congresos.Model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nombre;

    private String apellido;

    @OneToMany
    private List<Trabajo> trabajo;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public List<Trabajo> getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(List<Trabajo> trabajo) {
        this.trabajo = trabajo;
    }
}
