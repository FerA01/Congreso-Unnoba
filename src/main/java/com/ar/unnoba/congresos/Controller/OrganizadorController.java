package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Service.IOrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("admin")
@RequestMapping("/admin")
@EnableGlobalMethodSecurity(securedEnabled=true)
public class OrganizadorController {
    private final String role = "ROLE_ADMIN";
    @Autowired
    private IOrganizadorService organizadorService;

    @Autowired
    private EventoController evento;

    @Autowired
    public OrganizadorController(EventoController evento){
        this.evento = evento;
    }

    @GetMapping("/login")
    public String login(){
        return "organizador/login";
    }

    @GetMapping("/register")
    public String registrar(Model model){
        model.addAttribute("organizador", new Organizador());
        return "organizador/register";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, Authentication auth){
        try {
            Organizador usuario = (Organizador) auth.getPrincipal();
            if (id.equals(usuario.getId())){
                usuario.setId(id);
                model.addAttribute("role", role);
                model.addAttribute("usuario", usuario);
                model.addAttribute("id_user", usuario.getId());
                return "usuarios/usuario";
            }
            return "redirect:/accesso-denegado";
        }catch (Exception exception){
            model.addAttribute("error", "Hubo un problema al obtener el usuario");
            return "redirect:/eventos";
        }
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/edit")
    public String editar(@ModelAttribute("usuario") Organizador usuario, RedirectAttributes flash, Model model){
        if (usuario.getId() != null){
            try {
                organizadorService.save2(usuario);
                return "redirect:/admin/edit/" + usuario.getId();
            }catch (Exception e){
                flash.addFlashAttribute("danger", "El email ya se encuentra en uso");
                return "redirect:/admin/edit/" + usuario.getId();
            }
        }else{
            flash.addFlashAttribute("danger", "El email ya se encuentra en uso");
            return "redirect:/admin/edit/" + usuario.getId();
        }
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
    @Secured("ROLE_ADMIN")
    @GetMapping("/eventos")
    public String index(Model model, Authentication auth){
        return evento.eventos(1,6,model,auth);
    }

    /*
    @Secured("ROLE_ADMIN")
    @GetMapping("/eventos/new")
    public String newEvento(Model model){
        return evento.nuevoEvento(model);
    }

     */
    @PostMapping("/eventos")
    public String crearEvento(@ModelAttribute Evento evento){
        return this.evento.create(evento);
    }
    /*
    @Secured("ROLE_ADMIN")
    @GetMapping("/eventos/{id}/edit")
    public String editarEvento(@PathVariable("id") Long id, Model model, RedirectAttributes flash){
        return evento.edit(id, model, flash);
    }

     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/eventos/{id}")
    public String editarPost(@PathVariable("id") Long id, @ModelAttribute Evento evento, RedirectAttributes flash){
        return this.evento.evento(id, evento, flash);
    }
    @GetMapping("/eventos/{id}")
    public String listarEventos(@PathVariable("id") Long id,Model model, Authentication auth){
        return evento.verMas(id, model, auth);
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/eventos/{id}/delete")
    public String eliminarEvento(@PathVariable("id") Long id, RedirectAttributes flash){
        return evento.delete(id, flash);
    }
}
