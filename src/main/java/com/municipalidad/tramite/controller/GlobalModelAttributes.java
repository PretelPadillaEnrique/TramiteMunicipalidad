package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.service.UsuarioSesionService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final UsuarioSesionService usuarioSesionService;

    public GlobalModelAttributes(UsuarioSesionService usuarioSesionService) {
        this.usuarioSesionService = usuarioSesionService;
    }

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual() {
        return usuarioSesionService.getUsuarioActual();
    }
}
