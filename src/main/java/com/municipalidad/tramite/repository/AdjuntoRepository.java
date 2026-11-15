package com.municipalidad.tramite.repository;

import com.municipalidad.tramite.entity.AdjuntoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjuntoRepository extends JpaRepository<AdjuntoEntity, Long> {
    List<AdjuntoEntity> findByExpediente_Id(Long expedienteId);
    long countByExpediente_Id(Long expedienteId);
}
