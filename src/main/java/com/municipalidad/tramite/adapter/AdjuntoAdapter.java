package com.municipalidad.tramite.adapter;

import com.municipalidad.tramite.entity.AdjuntoEntity;
import com.municipalidad.tramite.model.Adjunto;
import org.springframework.stereotype.Component;

@Component
public class AdjuntoAdapter {

    private final ExpedienteAdapter expedienteAdapter;

    public AdjuntoAdapter(ExpedienteAdapter expedienteAdapter) {
        this.expedienteAdapter = expedienteAdapter;
    }

    public Adjunto toModel(AdjuntoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Adjunto(entity.getId(), entity.getNombreArchivo(), entity.getFechaCarga(),
                expedienteAdapter.toModel(entity.getExpediente()));
    }

    public AdjuntoEntity toEntity(Adjunto model) {
        if (model == null) {
            return null;
        }
        AdjuntoEntity entity = new AdjuntoEntity();
        entity.setId(model.getId());
        entity.setNombreArchivo(model.getNombreArchivo());
        entity.setFechaCarga(model.getFechaCarga());
        return entity;
    }
}
