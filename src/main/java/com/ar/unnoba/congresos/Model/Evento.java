package com.ar.unnoba.congresos.Model;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;
    //@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_hora_desde", nullable = false)
    private LocalDateTime fechaHoraDesde;
    //@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_hora_hasta", nullable = false)
    private LocalDateTime fechaHoraHasta;
    @Column(name = "modalidad", nullable = false)
    private String modalidad;
    @Column(name = "descripcion", length = 2200)
    private String descripcion;

    @OneToMany(mappedBy = "evento")
    private List<LlamadoPresentacion> llamadosPresentacion;
    @Column(name = "trabajos")
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
    private List<Trabajo> trabajos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public LocalDateTime getFechaHoraDesde() {
        return fechaHoraDesde;
    }
    public void setFechaHoraDesde(LocalDateTime fechaHoraDesde) { this.fechaHoraDesde = fechaHoraDesde; }
    public LocalDateTime getFechaHoraHasta() {
        return fechaHoraHasta;
    }
    public void setFechaHoraHasta(LocalDateTime fechaHoraHasta) {
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
    public List<Trabajo> getTrabajos() { return trabajos; }
    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }
}
