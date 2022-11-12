package com.ar.unnoba.congresos.Model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizador_eventos")
public class OrganizadorEventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Evento evento;
    @OneToMany
    private List<Organizador> organizadores;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public List<Organizador> getOrganizadores() { return organizadores; }
    public void setOrganizadores(List<Organizador> organizadores) { this.organizadores = organizadores; }
}
