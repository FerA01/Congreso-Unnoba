package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcercaDeController {
    @GetMapping("/acerca-de")
    public String acercaDe(Model model, Authentication auth){
        User user = (User) auth.getPrincipal();
        String role = "ROLE_USER";
        model.addAttribute("id_user", user.getId());
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            role = "ROLE_ADMIN";
        }
        model.addAttribute("role", role);
        return "acercaDe";
    }
}

