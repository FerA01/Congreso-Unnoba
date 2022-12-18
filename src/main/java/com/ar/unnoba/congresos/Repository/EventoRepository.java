package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    //public boolean hayTrabajos(Long id);
}
