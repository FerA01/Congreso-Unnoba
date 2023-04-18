package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.OrganizadorService;
import com.ar.unnoba.congresos.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@SessionAttributes("organizador")
@RequestMapping("/admin")
public class OrganizadorController {
    private final String role = "ROLE_ADMIN";
    @Autowired
    private OrganizadorService organizadorService;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoController evento;

    @Autowired
    public OrganizadorController(EventoController evento, OrganizadorService organizadorService, UsuarioService usuarioService){
        this.evento = evento;
        this.organizadorService = organizadorService;
        this.usuarioService = usuarioService;
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
            User organizador = (User) auth.getPrincipal();
            if (id.equals(organizador.getId())){
                model.addAttribute("role", role);
                model.addAttribute("organizador", organizador);
                model.addAttribute("id_user", organizador.getId());
                return "organizador/organizador";
            }
            return "redirect:/accesso-denegado";
        }catch (Exception exception){
            model.addAttribute("error", "Hubo un problema al obtener el usuario");
            return "redirect:/eventos";
        }
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/edit")
    public String editar(@ModelAttribute Organizador organizador, RedirectAttributes flash, Model model){
        if (organizador.getId() != null){
            try {
                organizadorService.save2(organizador);
                flash.addFlashAttribute("success", "Administrador editado correctamente");
                return "redirect:/admin/edit/" + organizador.getId();
            }catch (Exception e){
                flash.addFlashAttribute("fail", "El email ya se encuentra en uso");
                return "redirect:/admin/edit/" + organizador.getId();
            }
        }else{
            flash.addFlashAttribute("fail", "El email ya se encuentra en uso");
            return "redirect:/usuarios/edit/" + organizador.getId();
        }
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/user/edit/{id}")
    public String editarUser(   @PathVariable("id") Long id
                                , Model model
                                , Authentication auth
                                , RedirectAttributes flash){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()){
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("role", role);
            model.addAttribute("id_user",  ((Organizador) auth.getPrincipal()).getId());
            return "usuarios/editarUsuarioAdmin";
        }
        flash.addFlashAttribute("fail", "No se encontro el usuario");
        return "redirect:/usuarios";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/user/edit/{id}")
    public String actualizarUser(@PathVariable("id") Long id
                                ,@ModelAttribute("usuario") Usuario usuario
                                , RedirectAttributes flash){
        try {
            Optional<Usuario> usuarioBd = usuarioService.findById(id);
            if (usuarioBd.isPresent()) {
                usuarioBd.get().setNombre(usuario.getNombre());
                usuarioBd.get().setApellido(usuario.getApellido());
                usuarioService.save2(usuarioBd.get());
                flash.addFlashAttribute("success", "Usuario " + usuarioBd.get().getEmail() + " editado correctamente.");
                return "redirect:/usuarios";
            }
            flash.addFlashAttribute("fail", "Error al intentar editar al usuario.");
            return "redirect:/usuarios";
        }catch (Exception exception){
            flash.addFlashAttribute("fail", "Error al intentar editar al usuario.");
            return "redirect:/usuarios";
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
    @PostMapping("/eventos")
    public String crearEvento(@ModelAttribute Evento evento){
        return this.evento.create(evento);
    }
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
