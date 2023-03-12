package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Evento;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IEventoService {
    public Evento create(Evento evento);
    public List<Evento> getAll();
    public void delete(Long id);
    public Optional<Evento> findById(Long id);
    public Evento getById(Long id);
    public void save2(Evento evento);

    Page<Evento> getEventosActivos(int page, int size);

    Page<Evento> getEventos(int page, int size);
}
