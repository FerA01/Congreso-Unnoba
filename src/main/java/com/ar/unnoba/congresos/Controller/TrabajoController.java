package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/trabajos")
public class TrabajoController {
    @Autowired
    private ITrabajoService trabajoService;

    @Autowired
    public TrabajoController(ITrabajoService trabajoService, IEventoService eventoService){
        this.trabajoService = trabajoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/new")
    public String nuevoTrabajo(Model model){
        model.addAttribute("trabajo", new Trabajo());
        return "trabajos/subirPresentacion";
    }
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/new")
    Long uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        Long image = trabajoService.uploadImage(file);
        if (image != null){
            return image;
        }
        return null;
    }

    @GetMapping("/download/{id_file}")
    public ResponseEntity<?> downloadImage(@PathVariable("id_file") Long id_file){
        Resource file = trabajoService.downloadImage(id_file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
/*
    @GetMapping
    public String trabajos(Model model){
        List<Trabajo> trabajos = trabajoService.getAll();
        model.addAttribute("trabajos", trabajos);
        return "trabajos/presentaciones";
    }

    @GetMapping("/new")
    public String nuevoTrabajo(Model model){
        model.addAttribute("trabajo", new Trabajo());
        return "trabajos/agregarPresentacion";
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

 */
}
