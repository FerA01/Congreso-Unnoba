package com.ar.unnoba.congresos.Model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "llamado_presentacion")
public class LlamadoPresentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Evento evento;
    @Column(name = "fecha_hora_desde", nullable = false)
    private Date fechaHoraDesde;
    @Column(name = "fecha_hora_hasta", nullable = false)
    private Date fechaHoraHasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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
}
