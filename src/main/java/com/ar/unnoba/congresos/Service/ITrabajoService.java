package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Trabajo;

import java.util.Optional;

public interface ITrabajoService {
    Optional<Trabajo> findById(Long id);
    Trabajo save2(Trabajo trabajo);
}
