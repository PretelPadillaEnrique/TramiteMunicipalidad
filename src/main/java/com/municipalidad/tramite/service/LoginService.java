package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.adapter.UsuarioAdapter;
import com.municipalidad.tramite.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final UsuarioSesionService usuarioSesionService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAdapter usuarioAdapter;

    public LoginService(UsuarioSesionService usuarioSesionService, UsuarioRepository usuarioRepository,
                        UsuarioAdapter usuarioAdapter) {
        this.usuarioSesionService = usuarioSesionService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAdapter = usuarioAdapter;
    }

    @Transactional(readOnly = true)
    public Usuario validar(String correo, String password) {
        usuarioSesionService.setUsuarioActual(null);
        return usuarioRepository.findByCorreoAndPasswordAndEstado(correo, password, "Activo")
                .map(usuarioAdapter::toModel)
                .map(usuario -> {
                    usuarioSesionService.setUsuarioActual(usuario);
                    return usuario;
                })
                .orElse(null);
    }

    public void cerrarSesion() {
        usuarioSesionService.setUsuarioActual(null);
    }
}
