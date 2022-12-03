package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {
    //public Trabajo create(Trabajo trabajo);
    //public Trabajo getByIdEvento(Long id);
}
