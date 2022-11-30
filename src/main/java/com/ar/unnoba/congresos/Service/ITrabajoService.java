package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import java.util.List;

public interface ITrabajoService {
    public Trabajo create(Trabajo trabajo);
    public List<Trabajo> getAll();
    public void delete(Long id);
    public Trabajo getById(Long id);
}
