package com.ar.unnoba.congresos.Repository;
import com.ar.unnoba.congresos.Model.OrganizadorEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorEventosRepository extends JpaRepository<OrganizadorEventos, Long> {

}


