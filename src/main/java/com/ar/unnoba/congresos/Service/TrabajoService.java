package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;
import com.ar.unnoba.congresos.Repository.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.Instant;

@Service
public class TrabajoService implements ITrabajoService {
    @Autowired
    private TrabajoRepository repository;

    @Override
    public Long uploadImage(MultipartFile multipartImage) throws Exception {
        Trabajo dbImage = new Trabajo();
        dbImage.setNombre(multipartImage.getName());
        dbImage.setArchivo(multipartImage.getBytes());
        dbImage.setEstado("PENDIENTE");
        dbImage.setFecha_hora(Date.from(Instant.now()));
        return repository.save(dbImage)
                         .getId();
    }

    @Override
    public Resource downloadImage(Long imageId) {
        byte[] image = repository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getArchivo();

        return new ByteArrayResource(image);
    }
}
