package com.ar.unnoba.congresos.utils;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;

public class UserUtil {
    private static String roleAdmin = "ROLE_ADMIN";
    private static String roleUser = "ROLE_USER";
    public static void determinarUser(Model model, Authentication auth){
        User usuario;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority(roleAdmin))){
            usuario = (Organizador) auth.getPrincipal();
            model.addAttribute("role", roleAdmin);
            model.addAttribute("id_user", usuario.getId());
        }else{
            usuario = (Usuario) auth.getPrincipal();
            model.addAttribute("role", roleUser);
            model.addAttribute("id_user", usuario.getId());
        }
    }
}
