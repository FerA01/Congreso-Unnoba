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
import java.util.Optional;

@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, EventoController evento){ setUsuarioService(usuarioService); }

    @GetMapping
    public String index(Model model, Authentication auth){
        //Usuario usuario = (Usuario) auth.getPrincipal();
        List<Usuario> usuarios = usuarioService.getAll();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioRegistro", new Usuario());
        return "usuarios/listaUsuarios";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash, Authentication auth){
        try{
            usuarioService.delete(id);
            flash.addFlashAttribute("success","Usuario borrado correctamente.");
            return "redirect:/usuarios";
        }catch (Exception e){
            flash.addFlashAttribute("fail","Error al intentar borrar un usuario");
            return "redirect:/usuarios";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Optional<Usuario> usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("mensaje", "Editar usuario");
        return "usuarios/editarUsuario";
    }
    @PostMapping("/edit")
    public String editar(@ModelAttribute Usuario usuario, RedirectAttributes flash, Model model){
        if (usuario.getId() != null){
            try {
                usuarioService.save2(usuario);
                return "redirect:/usuarios";
            }catch (Exception e){
                flash.addFlashAttribute("danger", "El email ya se encuentra en uso");
                return "redirect:/usuarios/edit/" + usuario.getId();
            }
        }else{
            flash.addFlashAttribute("danger", "El email ya se encuentra en uso");
            return "redirect:/usuarios/edit/" + usuario.getId();
        }
    }

    @PostMapping("/register/new")
    public String create(@ModelAttribute("usuarioRegistro") Usuario usuarioRegistro, RedirectAttributes flash){
        try{
            usuarioService.create(usuarioRegistro);
            flash.addFlashAttribute("success", "Usuario registrado correctamente");
            return "redirect:/usuarios";
        }catch (Exception exception){
            flash.addFlashAttribute("fail", "Error al intentar registrar un usuario");
            return "redirect:/usuarios";
        }
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuarios/register";
    }
    public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService; }
}
