package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private IEventoService service;

    @Autowired
    private ITrabajoService trabajoService;

    @Autowired
    private TrabajoController trabajoController;

    @Autowired
    public EventoController(IEventoService service){ this.service = service; }

    @GetMapping
    public String eventos(Model model, Authentication auth){ //index
        List<Evento> eventos = service.getAll();
       // Usuario usuario = (Usuario) auth.getPrincipal();
        //Trabajo trabajo = trabajoService.obtenerTrabajo(ev);
        model.addAttribute("eventos", eventos);
        return "eventos/eventos";
    }
    @GetMapping("/eventosAdmin")
    public String eventosAdmin(Model model){ //index
        List<Evento> eventos = service.getAll();
        model.addAttribute("eventos", eventos);
        return "eventos/eventosAdmin";
    }
    @GetMapping("/{id}")
    public String verMas(@PathVariable("id") Long id,Model model){
        Evento evento = service.getById(id);
        model.addAttribute("evento", evento);
        return "eventos/evento";
    }

    @GetMapping("/{id_evento}/trabajos")
    public String verPresentacion(@PathVariable("id_evento") Long id){

        return "trabajos/presentacion";
    }
    @GetMapping("/{id_evento}/trabajos/new")
    public String nuevaPresentacion(@PathVariable("id_evento") Long id, Model model, RedirectAttributes flash, Authentication auth){
        //return trabajoController.nuevoTrabajo(model);
        Usuario usuario = (Usuario) auth.getPrincipal();
        Evento evento = service.getById(id);
        LocalDateTime hoy =  LocalDateTime.now();
        if (hoy.isBefore(evento.getFechaHoraDesde())){
            model.addAttribute("usuario", usuario);
            model.addAttribute("evento", evento);
            model.addAttribute("trabajo", new Trabajo());
            return "trabajos/agregarPresentacion";
        }
        flash.addFlashAttribute("info", "Ya paso la fecha de entregar de trabajos");
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

    @Transactional
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash){
        try{
            Optional<Evento> evento = service.findById(id);
            if (id > 0 && !hayTrabajos(evento.get())){
                //mensaje seguro quiere eliminar el evento??
                service.delete(id);
                flash.addFlashAttribute("success", "Evento eliminado correctamente");
                return "redirect:/eventos/eventosAdmin";
            }
            //No se puede borrar el evento ya que contiene trabajos de autores.
            flash.addFlashAttribute("danger", "No se puede eliminar el evento ya que tiene trabajos");
            return "redirect:/eventos/eventosAdmin";
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/eventos/eventosAdmin";
    }

    /**COMPROBAR QUE UN EVENTO NO TENGA TRABAJOS**/
    private boolean hayTrabajos(Evento evento){
        if (evento.getId() != null){
            return evento.getTrabajos().isEmpty();
        }
        return false;
    }
}
