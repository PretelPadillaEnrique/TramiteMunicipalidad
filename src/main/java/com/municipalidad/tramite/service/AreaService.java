package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.adapter.AreaAdapter;
import com.municipalidad.tramite.entity.AreaEntity;
import com.municipalidad.tramite.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    private final AreaAdapter areaAdapter;

    public AreaService(AreaRepository areaRepository, AreaAdapter areaAdapter) {
        this.areaRepository = areaRepository;
        this.areaAdapter = areaAdapter;
    }

    public List<Area> listar() {
        return areaRepository.findAll().stream()
                .map(areaAdapter::toModel)
                .collect(Collectors.toList());
    }

    public List<Area> listarActivas() {
        return listar().stream()
                .filter(area -> "Activo".equals(area.getEstado()))
                .collect(Collectors.toList());
    }

    public Area buscarPorId(Long id) {
        return areaRepository.findById(id)
                .map(areaAdapter::toModel)
                .orElse(null);
    }

    public void registrar(Area area) {
        area.setEstado("Activo");
        AreaEntity entity = areaAdapter.toEntity(area);
        entity.setId(null);
        areaRepository.save(entity);
    }

    public void actualizar(Area datos) {
        Optional<AreaEntity> existente = areaRepository.findById(datos.getId());
        existente.ifPresent(entity -> {
            entity.setNombre(datos.getNombre());
            entity.setDescripcion(datos.getDescripcion());
            areaRepository.save(entity);
        });
    }

    public void desactivar(Long id) {
        areaRepository.findById(id).ifPresent(entity -> {
            entity.setEstado("Inactivo");
            areaRepository.save(entity);
        });
    }
}
