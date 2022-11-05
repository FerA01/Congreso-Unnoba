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
    private String nombre;
    private Date fecha_desde;
    private Date fecha_hasta;
    private String modalidad;
    private String descripcion;

    @OneToMany(mappedBy = "eventos")
    private List<LlamadoPresentacion> llamadosPresentacion;

    @OneToMany(mappedBy = "eventos")
    private List<Trabajo> trabajos;


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

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
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

    public void setLlamadosPresentacion(List<LlamadoPresentacion> llamadosPresentacion) {
        this.llamadosPresentacion = llamadosPresentacion;
    }

    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }
}
