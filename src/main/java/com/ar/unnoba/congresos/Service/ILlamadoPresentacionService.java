package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.LlamadoPresentacion;

import java.util.List;

public interface ILlamadoPresentacionService {
    public LlamadoPresentacion create(LlamadoPresentacion llamadoPresentacion);
    public List<LlamadoPresentacion> getAll();
    public void delete(Long id);
}
