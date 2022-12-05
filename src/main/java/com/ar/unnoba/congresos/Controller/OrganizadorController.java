package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.IOrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("admin")
@RequestMapping("/admin")
public class OrganizadorController {
    @Autowired
    private IOrganizadorService organizadorService;

    @Autowired
    private EventoController evento;

    @Autowired
    public OrganizadorController(EventoController evento){
        this.evento = evento;
    }

    @GetMapping("/register")
    public String registrar(Model model){
        model.addAttribute("organizador", new Organizador());
        return "organizador/register";
    }
    @PostMapping("/register/new")
    public String registrarPost(@ModelAttribute Organizador organizador){
        if (organizador.getId() == null){
            organizadorService.create(organizador);
            return "redirect:/admin/eventos";
        }
        return "redirect:/admin/eventos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash, Authentication auth){
        Organizador usuario = (Organizador) auth.getPrincipal();
        if (usuario.getId().equals(id)){
            flash.addFlashAttribute("danger", "No puede eliminarse a si mismo.");
            return "redirect:/admin/eventos";
        }
        organizadorService.delete(id);
        flash.addFlashAttribute("success", "Admin eliminado correctamente");
        return "redirect:/admin/eventos";
    }



    /**Eventos**/
    @GetMapping("/eventos")
    public String index(Model model, Authentication auth){
        Organizador organizador = (Organizador) auth.getPrincipal();

        return evento.eventosAdmin(model);
    }

    @GetMapping("/eventos/new")
    public String newEvento(Model model){
        return evento.nuevoEvento(model);
    }
    @PostMapping("/eventos")
    public String crearEvento(@ModelAttribute Evento evento){
        return this.evento.create(evento);
    }
    @GetMapping("/eventos/{id}/edit")
    public String editarEvento(@PathVariable("id") Long id, Model model){
        return evento.edit(id, model);
    }
    @PostMapping("/eventos/{id}")
    public String editarPost(@PathVariable("id") Long id, @ModelAttribute Evento evento, Model model){
        return this.evento.evento(id, evento, model);
    }
    @GetMapping("/eventos/{id}")
    public String listarEventos(@PathVariable("id") Long id,Model model){
        return evento.verMas(id, model);
    }
    @PostMapping("/eventos/{id}/delete")
    public String eliminarEvento(@PathVariable("id") Long id, RedirectAttributes flash){
        return evento.delete(id, flash);
    }
}
