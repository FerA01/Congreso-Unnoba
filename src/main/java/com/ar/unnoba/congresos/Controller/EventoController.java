package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private IEventoService service;

    @Autowired
    public EventoController(IEventoService service){ this.service = service; }

    @GetMapping
    public String eventos(Model model){ //index
        List<Evento> eventos = service.getAll();
        model.addAttribute("eventos", eventos);
        return "eventos/eventos";
    }

    @GetMapping("/{id_evento}/presentacion")
    public String verPresentacion(@PathVariable("id_evento") Long id){

        return "trabajos/presentacion";
    }
    @GetMapping("/{id_evento}/presentacion/new")
    public String nuevaPresentacion(@PathVariable("id_evento") Long id, Model model){
        model.addAttribute("presentacion", new Trabajo());
        return "redirect:/eventos";
    }

    @GetMapping("/new")
    public String nuevoEvento(Model model){
        model.addAttribute("evento", new Evento());
        return "eventos/nuevoEvento";
    }

    @PostMapping
    public String create(@ModelAttribute Evento evento){
        service.create(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/{id}")
    public String evento(@PathVariable("id") Long id, Model model){
        if (id > 0){
            Evento evento = service.getById(id);
            model.addAttribute("evento", evento);
            return "redirect:/eventos/{id}";
        }
        return "redirect:/eventos";
    }
    @GetMapping("/eventos/{id}/edit")
    public String edit(@PathVariable("id") Long id){
        if (id > 0){
            Optional<Evento> evento = Optional.ofNullable(service.getById(id));
            return "redirect:/eventos";
        }
        return "redirect:/eventos";
    }
    @PostMapping("/eventos/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        Evento evento = service.getById(id);

        if (id > 0 && evento.getTrabajos().isEmpty()){
            //mensaje seguro quiere eliminar el evento??
            service.delete(id);
            return "redirect:/eventos";
        }
        //No se puede borrar el evento ya que contiene trabajos de autores.
        return "redirect:/eventos";
    }
}
