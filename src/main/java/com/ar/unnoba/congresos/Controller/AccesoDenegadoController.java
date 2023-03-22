package com.ar.unnoba.congresos.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoDenegadoController {
    @GetMapping("/accesso-denegado")
    public String accesoDenegado(){
        return "errores/error403";
    }
}
