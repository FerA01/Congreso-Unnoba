package com.ar.unnoba.congresos.Model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trabajos")
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha_hora;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Evento evento;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(Date fecha_hora) { this.fecha_hora = fecha_hora; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
