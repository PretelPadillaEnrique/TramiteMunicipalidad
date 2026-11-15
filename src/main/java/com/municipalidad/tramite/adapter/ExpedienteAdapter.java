package com.municipalidad.tramite.adapter;

import com.municipalidad.tramite.entity.ExpedienteEntity;
import com.municipalidad.tramite.model.Expediente;
import org.springframework.stereotype.Component;

@Component
public class ExpedienteAdapter {

    private final CategoriaAdapter categoriaAdapter;
    private final AreaAdapter areaAdapter;
    private final UsuarioAdapter usuarioAdapter;

    public ExpedienteAdapter(CategoriaAdapter categoriaAdapter, AreaAdapter areaAdapter,
                             UsuarioAdapter usuarioAdapter) {
        this.categoriaAdapter = categoriaAdapter;
        this.areaAdapter = areaAdapter;
        this.usuarioAdapter = usuarioAdapter;
    }

    public Expediente toModel(ExpedienteEntity entity) {
        if (entity == null) {
            return null;
        }
        Expediente model = new Expediente();
        model.setId(entity.getId());
        model.setFolio(entity.getFolio());
        model.setCiudadanoNombre(entity.getCiudadanoNombre());
        model.setCiudadanoDni(entity.getCiudadanoDni());
        model.setCiudadanoContacto(entity.getCiudadanoContacto());
        model.setCategoria(categoriaAdapter.toModel(entity.getCategoria()));
        model.setAreaActual(areaAdapter.toModel(entity.getAreaActual()));
        model.setUsuarioRegistro(usuarioAdapter.toModel(entity.getUsuarioRegistro()));
        model.setUsuarioUltimoCambio(usuarioAdapter.toModel(entity.getUsuarioUltimoCambio()));
        model.setFechaRegistro(entity.getFechaRegistro());
        model.setFechaLimite(entity.getFechaLimite());
        model.setMotivoReprogramacion(entity.getMotivoReprogramacion());
        model.setEstado(entity.getEstado());
        model.setActivo(entity.isActivo());
        return model;
    }

    public ExpedienteEntity toEntity(Expediente model) {
        if (model == null) {
            return null;
        }
        ExpedienteEntity entity = new ExpedienteEntity();
        entity.setId(model.getId());
        entity.setFolio(model.getFolio());
        entity.setCiudadanoNombre(model.getCiudadanoNombre());
        entity.setCiudadanoDni(model.getCiudadanoDni());
        entity.setCiudadanoContacto(model.getCiudadanoContacto());
        entity.setFechaRegistro(model.getFechaRegistro());
        entity.setFechaLimite(model.getFechaLimite());
        entity.setMotivoReprogramacion(model.getMotivoReprogramacion());
        entity.setEstado(model.getEstado());
        entity.setActivo(model.isActivo());
        return entity;
    }
}
