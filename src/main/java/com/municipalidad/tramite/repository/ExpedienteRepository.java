package com.municipalidad.tramite.repository;

import com.municipalidad.tramite.entity.ExpedienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpedienteRepository extends JpaRepository<ExpedienteEntity, Long> {
    Optional<ExpedienteEntity> findByFolio(String folio);
    List<ExpedienteEntity> findByEstadoAndFechaLimiteBefore(String estado, LocalDate fecha);
}
