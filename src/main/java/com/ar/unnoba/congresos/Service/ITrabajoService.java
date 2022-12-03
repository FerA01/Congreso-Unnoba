package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import java.util.List;

public interface ITrabajoService {
    public boolean create(Trabajo trabajo);
    public List<Trabajo> getAll();
    public void delete(Long id);
    public Trabajo getById(Long id);
    public void save2(Trabajo trabajo);
}
