package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private IEventoService service;

    @Autowired
    public EventoController(IEventoService service){ this.service = service; }

    @GetMapping
    public String eventos(Model model){ //index
        List<Evento> eventos = service.getAll();
        model.addAttribute("eventos", eventos);
        return "eventos/eventos";
    }

    @GetMapping("/eventos/new")
    public String nuevoEvento(Model model){
        model.addAttribute("evento", new Evento());
        return "eventos/nuevoEvento";
    }

    @PostMapping
    public String create(@ModelAttribute Evento evento){
        service.create(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/{id}/edit")
    public String edit(@PathVariable("id") Long id){
        return "";
    }
}
