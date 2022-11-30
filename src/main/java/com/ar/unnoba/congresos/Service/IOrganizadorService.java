package com.ar.unnoba.congresos.Service;

import com.ar.unnoba.congresos.Model.Organizador;

import java.util.List;

public interface IOrganizadorService {
    public Organizador create(Organizador organizador);
    public List<Organizador> getAll();
    public void delete(Long id);
}
