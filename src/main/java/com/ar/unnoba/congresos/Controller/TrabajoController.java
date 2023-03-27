package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Service.IEventoService;
import com.ar.unnoba.congresos.Service.ITrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

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
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        try {
            // Verificar si el archivo está vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            // Leer los datos del archivo
            String nombre = StringUtils.cleanPath(file.getOriginalFilename());
            String tipoContenido = file.getContentType();
            byte[] contenido = file.getBytes();

            // Crear una nueva entidad Archivo y almacenarla en la base de datos
            /*Archivo archivo = new Archivo();
            archivo.setNombre(nombre);
            archivo.setTipoContenido(tipoContenido);
            archivo.setContenido(contenido);
            archivoRepository.save(archivo);
            */

            Trabajo trabajo = new Trabajo();
            trabajo.setNombre(nombre);
            trabajo.setTipo(tipoContenido);
            trabajo.setArchivo(contenido);
            trabajo.setEstado("Pendiente");
            trabajo.setFecha_hora(Date.from(Instant.now()));
            trabajoService.save2(trabajo);

            return ResponseEntity.ok().body("Archivo subido correctamente");
        } catch (IOException ex) {
            return ResponseEntity.status(500).body("Ocurrió un error al subir el archivo");
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        // Obtener el archivo desde la base de datos
        Trabajo trabajo = trabajoService.findById(id).orElse(null);
        // Verificar si el archivo existe
        if (trabajo == null) {
            return ResponseEntity.notFound().build();
        }

        // Devolver el archivo en la respuesta
        ByteArrayResource resource = new ByteArrayResource(trabajo.getArchivo());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + trabajo.getNombre() + "\"")
                .contentType(MediaType.parseMediaType(trabajo.getTipo()))
                .contentLength(resource.contentLength())
                .body(resource);
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
