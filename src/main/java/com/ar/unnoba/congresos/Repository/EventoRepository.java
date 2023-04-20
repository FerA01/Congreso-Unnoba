package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    //public boolean hayTrabajos(Long id);

    @Query("SELECT e FROM Evento e WHERE e.fechaHoraHasta >= :currentDate ORDER BY e.fechaHoraDesde")
    public Page<Evento> findAllEventosActivos(Pageable pageable, @Param("currentDate") LocalDateTime creationDateTime);

    @Query("SELECT e FROM Evento e ORDER BY CASE " +
            "WHEN e.fechaHoraHasta < CURRENT_TIMESTAMP THEN 3 " +
            "WHEN e.fechaHoraDesde > CURRENT_TIMESTAMP THEN 2 " +
            "ELSE 1 END, e.fechaHoraDesde ASC")
    public Page<Evento> findAllEventos(Pageable pageable);

}
