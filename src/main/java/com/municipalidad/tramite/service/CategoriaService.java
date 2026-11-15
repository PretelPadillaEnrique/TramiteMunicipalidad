package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.model.Categoria;
import com.municipalidad.tramite.adapter.CategoriaAdapter;
import com.municipalidad.tramite.entity.AreaEntity;
import com.municipalidad.tramite.entity.CategoriaEntity;
import com.municipalidad.tramite.repository.AreaRepository;
import com.municipalidad.tramite.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final AreaRepository areaRepository;
    private final CategoriaAdapter categoriaAdapter;

    public CategoriaService(CategoriaRepository categoriaRepository, AreaRepository areaRepository,
                            CategoriaAdapter categoriaAdapter) {
        this.categoriaRepository = categoriaRepository;
        this.areaRepository = areaRepository;
        this.categoriaAdapter = categoriaAdapter;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll().stream()
                .map(categoriaAdapter::toModel)
                .collect(Collectors.toList());
    }

    public List<Categoria> listarActivas() {
        return categoriaRepository.findByEstado("Activo").stream()
                .map(categoriaAdapter::toModel)
                .collect(Collectors.toList());
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaAdapter::toModel)
                .orElse(null);
    }

    public void registrar(Categoria categoria, Long idArea) {
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new IllegalArgumentException("No existe el área " + idArea));
        categoria.setEstado("Activo");
        CategoriaEntity entity = categoriaAdapter.toEntity(categoria);
        entity.setId(null);
        entity.setArea(area);
        categoriaRepository.save(entity);
    }

    public void actualizar(Categoria datos, Long idArea) {
        CategoriaEntity entity = categoriaRepository.findById(datos.getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe la categoría " + datos.getId()));
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new IllegalArgumentException("No existe el área " + idArea));
        entity.setNombre(datos.getNombre());
        entity.setPlazoAtencion(datos.getPlazoAtencion());
        entity.setArea(area);
        categoriaRepository.save(entity);
    }

    public void desactivar(Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la categoría " + id));
        entity.setEstado("Inactivo");
        categoriaRepository.save(entity);
    }
}
