package com.ar.unnoba.congresos.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping({"/","/login"})
    public String login(@RequestParam(value = "error",required = false) String error, Model model){
        if(error!=null){
            model.addAttribute("danger","Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
        }
        return "login";
    }
    @PostMapping("/login")
    public String ingresar(){
        return "redirect:/users";
    }
}
