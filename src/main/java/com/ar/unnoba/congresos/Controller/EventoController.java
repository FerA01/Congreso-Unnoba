package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    @GetMapping("/eventosAdmin")
    public String eventosAdmin(Model model){ //index
        List<Evento> eventos = service.getAll();
        model.addAttribute("eventos", eventos);
        return "eventos/eventosAdmin";
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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        if (id > 0){
            /*
            Optional<Evento> evento = service.findById(id);
            AtomicReference<Evento> evento1 = null;
            evento.ifPresent(accion ->{
                evento1.set(new Evento());
                evento1.get().setId(evento.get().getId());
                evento1.get().setNombre(evento.get().getNombre());
                evento1.get().setModalidad(evento.get().getModalidad());
                evento1.get().setFechaHoraDesde(evento.get().getFechaHoraDesde());
                evento1.get().setFechaHoraHasta(evento.get().getFechaHoraHasta());
            });
             */
            Evento evento = service.getById(id);
            model.addAttribute("evento", evento);
            return "eventos/editarEvento";
        }
        return "eventos/editarEvento";
    }
    @PostMapping("/{id}")
    public String evento(@PathVariable("id") Long id, @ModelAttribute Evento evento, Model model){
        if (evento.getId() != null){
            service.save2(evento);
            //model.addAttribute("evento", evento);
            return "redirect:/eventos/eventosAdmin";
        }
        return "redirect:/eventos/eventosAdmin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash){
        Optional<Evento> evento = service.findById(id);
        if (id > 0 && noHayTrabajos(evento)){
            //mensaje seguro quiere eliminar el evento??
            service.delete(id);
            flash.addFlashAttribute("succes", "Evento eliminado correctamente");
            return "redirect:/eventos/eventosAdmin";
        }
        //No se puede borrar el evento ya que contiene trabajos de autores.
        flash.addFlashAttribute("danger", "No se puede eliminar el evento ya que tiene trabajos");
        return "redirect:/eventos/eventosAdmin";
    }

    /**COMPROBAR QUE UN EVENTO NO TENGA TRABAJOS**/
    private boolean noHayTrabajos(Optional<Evento> evento){
        if (evento.isPresent()){
            return evento.get().getTrabajos().isEmpty();
        }
        return false;
    }
}
