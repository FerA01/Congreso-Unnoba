package com.ar.unnoba.congresos.Repository;

import com.ar.unnoba.congresos.Model.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {
    public Organizador findByEmail(String email);
}
