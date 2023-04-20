package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.OrganizadorService;
import com.ar.unnoba.congresos.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class UsuarioController {
    private final String role = "ROLE_USER";
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private OrganizadorService organizadorService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, OrganizadorService organizadorService) {
        this.usuarioService = usuarioService;
        this.organizadorService = organizadorService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String index(Model model, Authentication auth) {
        List<Usuario> usuarios = usuarioService.getAll();
        User user = (User) auth.getPrincipal();
        model.addAttribute("id_user", user.getId());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioRegistro", new Usuario());
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            model.addAttribute("role", "ROLE_ADMIN");
        } else {
            model.addAttribute("role", "ROLE_USER");
        }
        return "usuarios/listaUsuarios";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash, Authentication auth) {
        try {
            usuarioService.delete(id);
            flash.addFlashAttribute("success", "Usuario borrado correctamente.");
            return "redirect:/usuarios";
        } catch (Exception e) {
            flash.addFlashAttribute("fail", "Error al intentar borrar un usuario");
            return "redirect:/usuarios";
        }
    }

    //TODO Un usuario puede editarse a si mismo, pero no puede editar a otros usuarios.
    //Todo Un admin si puede editar a cualquier usuario.
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, Authentication auth) {
        try {
            User usuario = (User) auth.getPrincipal();
            if (id.equals(usuario.getId())) {
                model.addAttribute("role", role);
                model.addAttribute("usuario", usuario);
                model.addAttribute("id_user", usuario.getId());
                return "usuarios/usuario";
            }
            return "redirect:/accesso-denegado";
        } catch (Exception exception) {
            model.addAttribute("error", "Hubo un problema al obtener el usuario");
            return "redirect:/eventos";
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/edit")
    public String editar(@ModelAttribute Usuario usuario, RedirectAttributes flash, Model model) {
        if (usuario.getId() != null) {
            try {
                usuarioService.save2(usuario);
                flash.addFlashAttribute("success", "Usuario editado correctamente");
                return "redirect:/usuarios/edit/" + usuario.getId();
            } catch (Exception e) {
                flash.addFlashAttribute("fail", "El email ya se encuentra en uso");
                return "redirect:/usuarios/edit/" + usuario.getId();
            }
        } else {
            flash.addFlashAttribute("fail", "El email ya se encuentra en uso");
            return "redirect:/usuarios/edit/" + usuario.getId();
        }
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/user/edit")
    public String editUserAdmin(  @ModelAttribute("usuario") Usuario usuario
                                , Model model
                                , Authentication auth
                                , RedirectAttributes flash){
        if (usuario.getId() != null) {
            try {
                usuarioService.save2(usuario);
                flash.addFlashAttribute("success", "Usuario editado correctamente");
                return "redirect:/usuarios";
            } catch (Exception e) {
                flash.addFlashAttribute("fail", "El email ya se encuentra en uso");
                return "redirect:/usuarios";
            }
        } else {
            flash.addFlashAttribute("fail", "El email ya se encuentra en uso");
            return "redirect:/usuarios";
        }
    }
    @PostMapping("/register/new")
    public String create(@ModelAttribute("usuarioRegistro") Usuario usuarioRegistro, RedirectAttributes flash) {
        try {
            usuarioService.create(usuarioRegistro);
            flash.addFlashAttribute("success", "Usuario registrado correctamente");
            return "redirect:/usuarios";
        } catch (Exception exception) {
            flash.addFlashAttribute("fail", "Error al intentar registrar un usuario");
            return "redirect:/usuarios";
        }
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes flash) {
        try {
            usuarioService.create(usuario);
            flash.addFlashAttribute("success", "Usuario registrado correctamente");
            return "redirect:/login";
        } catch (Exception exception) {
            flash.addFlashAttribute("fail", "Error al intentar registrar un usuario");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/register";
    }
}
