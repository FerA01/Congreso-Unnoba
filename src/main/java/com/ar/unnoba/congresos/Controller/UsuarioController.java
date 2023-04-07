package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
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
import java.util.Optional;

@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, EventoController evento){ setUsuarioService(usuarioService); }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String index(Model model, Authentication auth){
        List<Usuario> usuarios = usuarioService.getAll();
        User user = (User) auth.getPrincipal();
        model.addAttribute("id_user", user.getId());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioRegistro", new Usuario());
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            model.addAttribute("role", "ROLE_ADMIN");
        }else{
            model.addAttribute("role", "ROLE_USER");
        }
        return "usuarios/listaUsuarios";
    }
    @Secured("ROLE_ADMIN")
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

    //TODO Un usuario puede editarse a si mismo, pero no puede editar a otros usuarios.
    //Todo Un admin si puede editar a cualquier usuario.
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, Authentication auth){
        Optional<Usuario> usuario =  usuarioService.findById(id);
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        /*
            Se verifica que el usuario exista y si el id del usuario en sesión es igual al id por parámetro.
            Esta verificación es para que un usuario con rol ROLE_USER no pueda editar otros usuarios.

            Si el usuario en sesión es rol ROLE_ADMIN puede editar sin restricciones cualquier usuario.
         */
        if (   (    usuario.isPresent() && (usuario.get().getId())
                                            .equals(
                                                    ((User) auth.getPrincipal()).getId()
                                            )
               )
                || isAdmin
        ){
            if (isAdmin){
                model.addAttribute("role", "ROLE_ADMIN");
            }else{
                model.addAttribute("role", "ROLE_USER");
            }
            model.addAttribute("id_user", usuario.get().getId());
            model.addAttribute("usuario", usuario.get());
            return "usuarios/usuario";
        }
        //No se encontro al usuario
        return "redirect:/usuarios";
    }
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping("/edit")
    public String editar(@ModelAttribute Usuario usuario, RedirectAttributes flash, Model model){
        if (usuario.getId() != null){
            try {
                usuarioService.save2(usuario);
                return "redirect:/usuarios/edit/" + usuario.getId();
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
