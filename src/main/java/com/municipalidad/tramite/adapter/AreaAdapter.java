package com.municipalidad.tramite.adapter;

import com.municipalidad.tramite.entity.AreaEntity;
import com.municipalidad.tramite.model.Area;
import org.springframework.stereotype.Component;

@Component
public class AreaAdapter {

    public Area toModel(AreaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Area(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getEstado());
    }

    public AreaEntity toEntity(Area model) {
        if (model == null) {
            return null;
        }

        AreaEntity entity = new AreaEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setEstado(model.getEstado());
        return entity;
    }
}
