package com.ar.unnoba.congresos.Service;

import com.ar.unnoba.congresos.Model.Usuario;

import java.util.List;

public interface IUsuarioService {
    public Usuario create(Usuario usuario);
    public List<Usuario> getAll();
    public void delete(Long id);
}
