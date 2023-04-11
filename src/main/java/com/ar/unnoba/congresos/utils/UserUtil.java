package com.ar.unnoba.congresos.utils;
import com.ar.unnoba.congresos.Model.Organizador;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IOrganizadorService;
import com.ar.unnoba.congresos.Service.IUsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;

public class UserUtil {
    private static String roleAdmin = "ROLE_ADMIN";
    private static String roleUser = "ROLE_USER";
    public static User determinarUser(    Model model
                                        , Authentication auth
                                        , IUsuarioService usuarioService
                                        , IOrganizadorService organizadorService){
        User usuario;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority(roleAdmin))){
            usuario = (Organizador) auth.getPrincipal();
            model.addAttribute("role", roleAdmin);
            model.addAttribute("id_user", usuario.getId());
            return organizadorService.findById(usuario.getId()).get();
        }else{
            usuario = (Usuario) auth.getPrincipal();
            model.addAttribute("role", roleUser);
            model.addAttribute("id_user", usuario.getId());
            return usuarioService.findById(usuario.getId()).get();
        }
    }
}
