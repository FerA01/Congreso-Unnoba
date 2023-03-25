package com.ar.unnoba.congresos.Service;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ITrabajoService {
    Long uploadImage(@RequestParam MultipartFile multipartImage) throws Exception;
    Resource downloadImage(@PathVariable Long imageId);
}
