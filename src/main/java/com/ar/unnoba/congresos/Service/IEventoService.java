package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Evento;

import java.util.List;

public interface IEventoService {
    public Evento create(Evento evento);
    public List<Evento> getAll();
    public void delete(Long id);
}
