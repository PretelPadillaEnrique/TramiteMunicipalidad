package com.municipalidad.tramite.adapter;

import com.municipalidad.tramite.entity.UsuarioEntity;
import com.municipalidad.tramite.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAdapter {

    private final AreaAdapter areaAdapter;

    public UsuarioAdapter(AreaAdapter areaAdapter) {
        this.areaAdapter = areaAdapter;
    }

    public Usuario toModel(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        Usuario model = new Usuario();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setCorreo(entity.getCorreo());
        model.setPassword(entity.getPassword());
        model.setRol(entity.getRol());
        model.setEstado(entity.getEstado());
        model.setArea(areaAdapter.toModel(entity.getArea()));
        return model;
    }

    public UsuarioEntity toEntity(Usuario model) {
        if (model == null) {
            return null;
        }
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setCorreo(model.getCorreo());
        entity.setPassword(model.getPassword());
        entity.setRol(model.getRol());
        entity.setEstado(model.getEstado());
        return entity;
    }
}
