package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import java.util.List;

public class EventoService implements IEventoService{
    @Autowired
    private EventoRepository repository;

    @Override
    public Evento create(Evento evento) {
        if (!repository.existsById(evento.getId())){


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
}
