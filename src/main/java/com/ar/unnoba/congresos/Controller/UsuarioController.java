package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Autowired
    public UsuarioController(){

    }

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuarios/test";
    }
}
