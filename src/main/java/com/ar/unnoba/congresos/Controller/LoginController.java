package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioController usuarioController;

    @GetMapping("/login")
    public String login(){
        return "/login";
    }
    @GetMapping("/register")
    public String register(Model model){
        return usuarioController.register(model);
    }
    @PostMapping("/register/new")
    public String create(@ModelAttribute Usuario usuario){
        //usuarioService.create(usuario);
        //return "redirect:/usuarios";
        return usuarioController.create(usuario);
    }

    public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService; }
}
