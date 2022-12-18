package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventos")
@EnableGlobalMethodSecurity(securedEnabled=true)
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
    @Secured("ROLE_ADMIN")
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

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String nuevoEvento(Model model){
        model.addAttribute("evento", new Evento());
        return "eventos/nuevoEvento";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping
    public String create(@ModelAttribute Evento evento){
        service.create(evento);
        return "redirect:/eventos";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes flash){
        if (id > 0){
            Evento evento = service.getById(id);
            model.addAttribute("evento", evento);
            flash.addFlashAttribute("success", "Evento editado correctamente");
            return "eventos/editarEvento";
        }
        return "eventos/editarEvento";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}")
    public String evento(@PathVariable("id") Long id, @ModelAttribute Evento evento, Model model){
        if (evento.getId() != null){
            service.save2(evento);
            //model.addAttribute("evento", evento);
            return "redirect:/admin/eventos";
        }
        return "redirect:/admin/eventos";
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash){
        try{
            Optional<Evento> evento = service.findById(id);
            if (id < 0 || hayTrabajos(evento.get())){
                //mensaje seguro quiere eliminar el evento??
                service.delete(id);
                flash.addFlashAttribute("success", "Evento eliminado correctamente");
                return "redirect:/admin/eventos";
            }
            //No se puede borrar el evento ya que contiene trabajos de autores.
            flash.addFlashAttribute("danger", "No se puede eliminar el evento ya que tiene trabajos");
            return "redirect:/admin/eventos";
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/eventos";
    }

    /**COMPROBAR QUE UN EVENTO NO TENGA TRABAJOS**/
    private boolean hayTrabajos(Evento evento){
        if (evento.getId() != null){
            return evento.getTrabajos().isEmpty();
        }
        return false;
    }
}
