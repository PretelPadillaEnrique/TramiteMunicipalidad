package com.municipalidad.tramite.adapter;

import com.municipalidad.tramite.entity.CategoriaEntity;
import com.municipalidad.tramite.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaAdapter {

    private final AreaAdapter areaAdapter;

    public CategoriaAdapter(AreaAdapter areaAdapter) {
        this.areaAdapter = areaAdapter;
    }

    public Categoria toModel(CategoriaEntity entity) {
        if (entity == null) {
            return null;
        }
        Categoria model = new Categoria();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setPlazoAtencion(entity.getPlazoAtencion());
        model.setEstado(entity.getEstado());
        model.setArea(areaAdapter.toModel(entity.getArea()));
        return model;
    }

    public CategoriaEntity toEntity(Categoria model) {
        if (model == null) {
            return null;
        }
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setPlazoAtencion(model.getPlazoAtencion());
        entity.setEstado(model.getEstado());
        return entity;
    }
}
