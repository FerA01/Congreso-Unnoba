package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService implements IEventoService{
    @Autowired
    private EventoRepository repository;


    @Override
    public Evento create(Evento evento) {
        if (evento.getId() == null){
            repository.save(evento);
        }
        return evento;
    }

    @Override
    public List<Evento> getAll() { return repository.findAll(
            Sort.by("nombre").ascending()
            .and(Sort.by("fechaHoraDesde").ascending())
    );
    }
    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Override
    public Optional<Evento> findById(Long id) { return repository.findById(id); }

    @Override
    @Deprecated
    public Evento getById(Long id) { return repository.getById(id); }

    @Override
    public void save2(Evento evento) { repository.save(evento); }

    @Override
    public Page<Evento> getEventosActivos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllEventosActivos(pageable, LocalDateTime.now());
    }

    @Override
    public Page<Evento> getEventos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllEventos(pageable);
    }


}
