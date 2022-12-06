package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.OrganizadorEventos;
import java.util.List;

public interface IOrganizadorEventosService {
    public OrganizadorEventos create(OrganizadorEventos usuario);
    public List<OrganizadorEventos> getAll();
    public void delete(Long id);
}