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

@Service
public class TrabajoService implements ITrabajoService {
    @Autowired
    private TrabajoRepository repository;

    @Override
    public Long uploadImage(MultipartFile multipartImage) throws Exception {
        Trabajo dbImage = new Trabajo();
        dbImage.setNombre(multipartImage.getName());
        dbImage.setArchivo(multipartImage.getBytes());

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
