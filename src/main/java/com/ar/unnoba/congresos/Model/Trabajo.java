package com.ar.unnoba.congresos.Model;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trabajos")
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_hora", nullable = false)
    private Date fecha_hora;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    private Evento evento;
    @Column(name = "estado", nullable = false)
    private String estado;
    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;
    @Column(name = "archivo")
    @Lob
    private byte[] archivo;
    @Column(name = "tipo", nullable = false)
    private String tipo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(Date fecha_hora) { this.fecha_hora = fecha_hora; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public byte[] getArchivo() { return archivo;}
    public void setArchivo(byte[] archivo) { this.archivo = archivo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
