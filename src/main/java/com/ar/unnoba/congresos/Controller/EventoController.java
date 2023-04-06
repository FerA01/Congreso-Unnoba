package com.ar.unnoba.congresos.Controller;

import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import com.ar.unnoba.congresos.Service.IPagingService;
import com.ar.unnoba.congresos.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventos")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class EventoController {
    @Autowired
    private IEventoService service;
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITrabajoService trabajoService;

    @Autowired
    private TrabajoController trabajoController;

    @Autowired
    private IPagingService IPagingService;

    @Autowired
    public EventoController(IEventoService service) {
        this.service = service;
    }

    @GetMapping
    public String eventos(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                          Model model, Authentication auth) { //index

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            model.addAttribute("role", "ROLE_ADMIN");
            return mostrarEventosAdmin(page,size,model);
        } else {
            model.addAttribute("role", "ROLE_USER");
            return mostrarEventosUsuario(page, size, model);
        }
    }

    @GetMapping("/{id}")
    public String verMas(@PathVariable("id") Long id, Model model, Authentication auth) {
        Evento evento = service.getById(id);
        model.addAttribute("evento", evento);
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (isAdmin){
            model.addAttribute("role","ROLE_ADMIN");
            return "eventos/evento";
        }
        User usuario = (Usuario) auth.getPrincipal();
        Optional<Usuario> usuario1 = usuarioService.findById(usuario.getId());
        Long hayTrabajos = trabajoService.countByUsuario(usuario1.get().getId());
        boolean subioTrabajos = hayTrabajos > 0;
        model.addAttribute("subioTrabajos", subioTrabajos);
        model.addAttribute("role","ROLE_USER");
        model.addAttribute("id_user", usuario.getId());
        return "eventos/evento";
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id_evento}/trabajos/new")
    public String nuevaPresentacion(@PathVariable("id_evento") Long id, Model model, RedirectAttributes flash, Authentication auth) {

        Usuario usuario = (Usuario) auth.getPrincipal();
        Evento evento = service.getById(id);
        LocalDateTime hoy = LocalDateTime.now();
        if (hoy.isBefore(evento.getFechaHoraHasta())) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("evento", evento);
            //return trabajoController.nuevoTrabajo(model);
            return "redirect:/eventos";
        }
        flash.addFlashAttribute("info", "Ya paso la fecha de entregar de trabajos");
        return "redirect:/eventos";
    }
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{id_evento}/trabajos/new")
    public Long upload(@RequestParam("file") MultipartFile multipartImage) throws Exception {
        return null; //trabajoController.uploadImage(multipartImage);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String nuevoEvento(Model model) {
        model.addAttribute("evento", new Evento());
        return "eventos/crearEvento";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public String create(@ModelAttribute Evento evento) {
        service.create(evento);
        return "redirect:/eventos";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        if (id > 0) {
            Evento evento = service.getById(id);
            model.addAttribute("evento", evento);
            flash.addFlashAttribute("success", "Evento editado correctamente");
            return "eventos/editarEvento";
        }
        return "eventos/editarEvento";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}")
    public String evento(@PathVariable("id") Long id, @ModelAttribute Evento evento, Model model) {
        if (evento.getId() != null) {
            service.save2(evento);
            //model.addAttribute("evento", evento);
            return "redirect:/admin/eventos";
        }
        return "redirect:/admin/eventos";
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash) {
        try {
            Optional<Evento> evento = service.findById(id);
            if (id < 0 || hayTrabajos(evento.get())) {
                //mensaje seguro quiere eliminar el evento??
                service.delete(id);
                flash.addFlashAttribute("success", "Evento eliminado correctamente");
                return "redirect:/admin/eventos";
            }
            //No se puede borrar el evento ya que contiene trabajos de autores.
            flash.addFlashAttribute("danger", "No se puede eliminar el evento ya que tiene trabajos");
            return "redirect:/admin/eventos";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/eventos";
    }

    /**
     * COMPROBAR QUE UN EVENTO NO TENGA TRABAJOS
     **/
    private boolean hayTrabajos(Evento evento) {
        if (evento.getId() != null) {
            return evento.getTrabajos().isEmpty();
        }
        return false;
    }

    private String mostrarEventosUsuario(int page, int size, Model model){
        Page<Evento> eventos = service.getEventosActivos(page - 1, size);
        model.addAttribute("eventos", eventos);

        int paginasTotales = eventos.getTotalPages();
        model.addAttribute("totalPages", paginasTotales);

        List<Integer> paginas = IPagingService.getPagingRange(page, paginasTotales, 5);
        model.addAttribute("pages", paginas);
        model.addAttribute("currentPage", page);

        return "eventos/eventos";
    }

    private String mostrarEventosAdmin(int page, int size, Model model){

        Page<Evento> eventos = service.getEventos(page - 1, size);
        model.addAttribute("eventos", eventos);

        int paginasTotales = eventos.getTotalPages();
        model.addAttribute("totalPages", paginasTotales);

        List<Integer> paginas = IPagingService.getPagingRange(page, paginasTotales, 5);
        model.addAttribute("pages", paginas);
        model.addAttribute("currentPage", page);
        return "eventos/eventosAdmin";
    }
}
