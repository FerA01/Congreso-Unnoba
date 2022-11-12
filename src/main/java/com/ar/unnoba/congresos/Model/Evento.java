package com.ar.unnoba.congresos.Model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "fecha_hora_desde", nullable = false)
    private Date fechaHoraDesde;
    @Column(name = "fecha_hora_hasta", nullable = false)
    private Date fechaHoraHasta;
    @Column(name = "modalidad", nullable = false)
    private String modalidad;
    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "evento")
    private List<LlamadoPresentacion> llamadosPresentacion;
    @OneToMany(mappedBy = "evento")
    private List<Trabajo> trabajos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getFechaHoraDesde() {
        return fechaHoraDesde;
    }
    public void setFechaHoraDesde(Date fechaHoraDesde) {
        this.fechaHoraDesde = fechaHoraDesde;
    }
    public Date getFechaHoraHasta() {
        return fechaHoraHasta;
    }
    public void setFechaHoraHasta(Date fechaHoraHasta) {
        this.fechaHoraHasta = fechaHoraHasta;
    }
    public String getModalidad() {
        return modalidad;
    }
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<LlamadoPresentacion> getLlamadosPresentacion() {
        return llamadosPresentacion;
    }
    public void setLlamadosPresentacion(List<LlamadoPresentacion> llamadosPresentacion) { this.llamadosPresentacion = llamadosPresentacion; }
    public List<Trabajo> getTrabajos() {
        return trabajos;
    }
    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }
}
