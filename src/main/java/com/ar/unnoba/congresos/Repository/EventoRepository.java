package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface EventoRepository extends JpaRepository<Evento, Long> {

}
