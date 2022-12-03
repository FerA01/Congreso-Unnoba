package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/trabajos")
public class TrabajoController {
    @Autowired
    private ITrabajoService trabajoService;

    @Autowired
    public TrabajoController(ITrabajoService trabajoService){ this.trabajoService = trabajoService; }

    @GetMapping
    public String trabajos(Model model){
        List<Trabajo> trabajos = trabajoService.getAll();
        model.addAttribute("trabajos", trabajos);
        return "trabajos/presentaciones";
    }

    @GetMapping("/new")
    public String nuevoTrabajo(Model model){
        model.addAttribute("trabajo", new Trabajo());
        return "trabajos/nuevoTrabajo";
    }

    @PostMapping
    public String create(@ModelAttribute Trabajo trabajo){
        trabajoService.create(trabajo);
        return "redirect:/trabajos";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        if (id > 0){
            Trabajo trabajo = trabajoService.getById(id);
            model.addAttribute("trabajo", trabajo);
            return "redirect:/trabajos/{id}";
        }
        return "redirect:/trabajos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash){
        trabajoService.delete(id);
        flash.addFlashAttribute("success", "Trabajo eliminado correctamente");
        return "redirect:/eventos";
    }
}
