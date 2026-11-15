package com.municipalidad.tramite.repository;

import com.municipalidad.tramite.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    List<CategoriaEntity> findByEstado(String estado);
}
