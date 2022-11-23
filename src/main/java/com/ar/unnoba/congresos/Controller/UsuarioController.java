package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IUsuarioService;
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
    private IUsuarioService usuarioService;

    @Autowired
    public UsuarioController(IUsuarioService usuarioService){
        setUsuarioService(usuarioService);
    }


    @GetMapping
    public String index(Model model, Authentication auth){
        List<Usuario> usuarios = getUsuarioService().getAll();
        model.addAttribute("usuarios", usuarios);
        return "index";
    }

    @PostMapping("/usuarios/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash, Authentication auth){
        Usuario usuario = (Usuario) auth.getPrincipal();
        Long idUsuario = usuario.getId();
        if (idUsuario.equals(id)){
            //flash.addAttribute("danger", "No puede eliminarse a si mismo.");
            return "redirect:/usuarios";
        }
        getUsuarioService().delete(id);
        //flash.addAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }
    /*
    @PostMapping("/register/new")
    public String create(@ModelAttribute Usuario usuario){
        getUsuarioService().create(usuario);
        return "redirect:/usuarios";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuarios/register";
    }
     */

    public IUsuarioService getUsuarioService() { return usuarioService; }
    public void setUsuarioService(IUsuarioService usuarioService) { this.usuarioService = usuarioService; }
}
