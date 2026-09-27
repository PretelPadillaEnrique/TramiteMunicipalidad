package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final DummyDataService dummyDataService;

    public UsuarioService(DummyDataService dummyDataService) {
        this.dummyDataService = dummyDataService;
    }

    public List<Usuario> listar() {
        return dummyDataService.getUsuarios();
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario usuario : dummyDataService.getUsuarios()) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    private Area buscarAreaPorId(Long idArea) {
        for (Area area : dummyDataService.getAreas()) {
            if (area.getId().equals(idArea)) {
                return area;
            }
        }
        return null;
    }

    public void registrar(Usuario usuario, Long idArea) {
        usuario.setArea(buscarAreaPorId(idArea));
        usuario.setEstado("Activo");
        dummyDataService.registrarUsuario(usuario);
    }

    public void actualizar(Usuario datos, Long idArea) {
        Usuario usuario = buscarPorId(datos.getId());
        if (usuario != null) {
            usuario.setNombre(datos.getNombre());
            usuario.setCorreo(datos.getCorreo());
            usuario.setRol(datos.getRol());
            usuario.setArea(buscarAreaPorId(idArea));
        }
    }

    public void desactivar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setEstado("Inactivo");
        }
    }
}
