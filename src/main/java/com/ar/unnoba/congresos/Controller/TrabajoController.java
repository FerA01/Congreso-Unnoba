package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Evento;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Model.User;
import com.ar.unnoba.congresos.Model.Usuario;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import com.ar.unnoba.congresos.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trabajos")
public class TrabajoController {
    @Autowired
    private final ITrabajoService trabajoService;
    @Autowired
    private final IEventoService eventoService;
    @Autowired
    private final IUsuarioService usuarioService;

    @Autowired
    public TrabajoController(ITrabajoService trabajoService, IEventoService eventoService, IUsuarioService usuarioService){
        this.trabajoService = trabajoService;
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id_evento}/{id_user}/new")
    public String nuevoTrabajo(   @PathVariable("id_evento") Long id_evento
                                , @PathVariable("id_user") Long id_user
                                , Model model){
        model.addAttribute("id_evento", id_evento);
        model.addAttribute("id_user", id_user);
        model.addAttribute("trabajo", new Trabajo());
        return "trabajos/subirPresentacion";
    }
    @GetMapping("/{id_user}/mis-presentaciones")
    public String presentaciones(@PathVariable("id_user") Long id_user, Model model, Authentication auth){
        try{
            boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            if (!isAdmin && id_user.equals(((Usuario) auth.getPrincipal()).getId())){
                Usuario usuario = usuarioService.findById(id_user).get();
                List<Trabajo> trabajos = trabajoService.findAllByUsuario(usuario);
                List<Trabajo> trabajos1 = new ArrayList<>();
                trabajos.forEach(
                        trabajo -> trabajos1.add(trabajoService.findById(trabajo.getId()).get())
                );
                usuario.setTrabajos(trabajos1);
                model.addAttribute("trabajos", usuario.getTrabajos());
                return "trabajos/presentacionesUsuario";
            }
            return "redirect:/eventos";
        }catch (Exception e){
            return "redirect:/eventos";
        }
    }
    @PostMapping("/{id_evento}/{id_user}/upload")
    public ResponseEntity<String> upload( @RequestParam("file") MultipartFile file
                                        , @PathVariable("id_evento") Long id_evento
                                        , @PathVariable("id_user") Long id_user
                                        , Authentication auth) {

        try {
            // Verificar si el archivo está vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            if (!isAdmin && id_user.equals(((User) auth.getPrincipal()).getId())){
                // Leer los datos del archivo
                String nombre = StringUtils.cleanPath(file.getOriginalFilename());
                String tipoContenido = file.getContentType();
                byte[] contenido = file.getBytes();
                Trabajo trabajo = new Trabajo();
                Usuario usuario = usuarioService.findById(id_user).get();
                Evento evento = eventoService.getById(id_evento);
                trabajo.setNombre(nombre);
                trabajo.setTipo(tipoContenido);
                trabajo.setArchivo(contenido);
                trabajo.setEstado("Pendiente");
                trabajo.setFecha_hora(Date.from(Instant.now()));
                trabajo.setUsuario(usuario);
                trabajo.setEvento(evento);
                trabajoService.save2(trabajo);
                return ResponseEntity.ok().body("Archivo subido correctamente");
            }
            return ResponseEntity.ok().body("Los administradores no pueden subir trabajos.");
        } catch (IOException ex) {
            return ResponseEntity.status(500).body("Ocurrió un error al subir el archivo");
        }
    }

    @GetMapping("/{id_evento}/{id_user}/download")
    public ResponseEntity<Resource> download(@PathVariable("id_evento") Long id_evento
                                            ,@PathVariable("id_user") Long id_user
                                            ,Authentication auth) {
        try {
            // Obtener el archivo desde la base de datos
            //Trabajo trabajo = trabajoService.findById(id).orElse(null);
            Long idTrabajo = trabajoService.findByUsuarioAndEvento(id_user, id_evento);
            Trabajo trabajo = trabajoService.findById(idTrabajo).orElse(null);
            // Verificar si el archivo existe
            if (trabajo == null) {
                return ResponseEntity.notFound().build();
            }
            if (id_user.equals( ((User) auth.getPrincipal()).getId())){
                // Devolver el archivo en la respuesta
                ByteArrayResource resource = new ByteArrayResource(trabajo.getArchivo());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + trabajo.getNombre() + "\"")
                        .contentType(MediaType.parseMediaType(trabajo.getTipo()))
                        .contentLength(resource.contentLength())
                        .body(resource);
            }
            return ResponseEntity.status(403).build();
        }catch (Exception exception){
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    @GetMapping("/{id_evento}/{id_user}/delete")
    public String delete( @PathVariable("id_evento") Long id_evento
                        , @PathVariable("id_user") Long id_user
                        , Authentication auth){
        try {
            Long idTrabajo = trabajoService.findByUsuarioAndEvento(id_user, id_evento);
            Optional<Trabajo> trabajo = trabajoService.findById(idTrabajo);
            if (id_user.equals(((User) auth.getPrincipal()).getId()) && trabajo.isPresent()){
                trabajoService.deleteById(trabajo.get().getId());
                return "redirect:/eventos/" + id_evento;
            }
            return "redirect:/accesso-denegado";
        }catch (Exception exception){
            return "redirect:/eventos/" + id_evento;
        }
    }
}
