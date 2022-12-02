package com.ar.unnoba.congresos.Service;
import com.ar.unnoba.congresos.Model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public Usuario create(Usuario usuario);
    public List<Usuario> getAll();
    public void delete(Long id);
    public Optional<Usuario> findById(Long id);
    public void save2(Usuario usuario);
}
