package com.ar.unnoba.congresos.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcercaDeController {
    @GetMapping("/acerca-de")
    public String acercaDe(){
        return "/acercaDe";
    }
}

