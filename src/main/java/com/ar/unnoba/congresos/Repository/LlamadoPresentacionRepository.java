package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.LlamadoPresentacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LlamadoPresentacionRepository extends JpaRepository<LlamadoPresentacion, Long> {
    //public LlamadoPresentacion create(LlamadoPresentacion llamadoPresentacion);
}
