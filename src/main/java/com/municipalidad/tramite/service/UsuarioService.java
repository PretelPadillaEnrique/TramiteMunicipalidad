package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.adapter.UsuarioAdapter;
import com.municipalidad.tramite.entity.AreaEntity;
import com.municipalidad.tramite.entity.UsuarioEntity;
import com.municipalidad.tramite.repository.AreaRepository;
import com.municipalidad.tramite.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final UsuarioAdapter usuarioAdapter;

    public UsuarioService(UsuarioRepository usuarioRepository, AreaRepository areaRepository,
                          UsuarioAdapter usuarioAdapter) {
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
        this.usuarioAdapter = usuarioAdapter;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll().stream()
                .map(usuarioAdapter::toModel)
                .collect(Collectors.toList());
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioAdapter::toModel)
                .orElse(null);
    }

    public void registrar(Usuario usuario, Long idArea) {
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new IllegalArgumentException("No existe el área " + idArea));
        usuario.setEstado("Activo");
        UsuarioEntity entity = usuarioAdapter.toEntity(usuario);
        entity.setId(null);
        entity.setArea(area);
        usuarioRepository.save(entity);
    }

    public void actualizar(Usuario datos, Long idArea) {
        UsuarioEntity entity = usuarioRepository.findById(datos.getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario " + datos.getId()));
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new IllegalArgumentException("No existe el área " + idArea));
        entity.setNombre(datos.getNombre());
        entity.setCorreo(datos.getCorreo());
        entity.setRol(datos.getRol());
        entity.setArea(area);
        usuarioRepository.save(entity);
    }

    public void desactivar(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario " + id));
        entity.setEstado("Inactivo");
        usuarioRepository.save(entity);
    }
}
