package com.ar.unnoba.congresos.Model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizadores")
public class Organizador{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String email;

    private String password;

    private String nombre;


    private String apellido;

    @ManyToMany()
    @JoinTable(name = "organizador_eventos",
            joinColumns = @JoinColumn(name = "eventos"),
            inverseJoinColumns = @JoinColumn(name="organizadores"))
    private List<Evento> eventos;


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
    public List<Evento> getEventos() { return eventos; }
    public void setEventos(List<Evento> eventos) { this.eventos = eventos; }
}