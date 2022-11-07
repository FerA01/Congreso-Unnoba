package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
    private IUsuarioService usuarioService;

    @Autowired
    public UsuarioController(IUsuarioService usuarioService){
        setUsuarioService(usuarioService);
    }

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuarios/test";
    }
    @GetMapping
    public String login(Model model){
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    public IUsuarioService getUsuarioService() { return usuarioService; }
    public void setUsuarioService(IUsuarioService usuarioService) { this.usuarioService = usuarioService; }
}
