package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        setUsuarioService(usuarioService);
    }


    @GetMapping
    public String index(Model model, Authentication auth){
        Usuario usuario = (Usuario) auth.getPrincipal();
        List<Usuario> usuarios = usuarioService.getAll();
        model.addAttribute("usuarios", usuarios);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash, Authentication auth){
        Usuario usuario = (Usuario) auth.getPrincipal();
        Long idUsuario = usuario.getId();
        if (usuario.getId().equals(id)){
            flash.addAttribute("danger", "No puede eliminarse a si mismo.");
            return "redirect:/usuarios";
        }
        usuarioService.delete(id);
        flash.addAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/edit/{id}")
    public String edit(@PathVariable("id") Long id, Authentication auth){

        return "redirect:/";
    }

    @PostMapping("/register/new")
    public String create(@ModelAttribute Usuario usuario){
        usuarioService.create(usuario);
        return "/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuarios/register";
    }


    public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService; }
}
