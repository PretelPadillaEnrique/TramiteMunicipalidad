package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.service.UsuarioSesionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AccesoInterceptor implements HandlerInterceptor {

    private final UsuarioSesionService usuarioSesionService;

    public AccesoInterceptor(UsuarioSesionService usuarioSesionService) {
        this.usuarioSesionService = usuarioSesionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ruta = request.getRequestURI();

        if (ruta.equals("/login") || ruta.equals("/") || ruta.equals("/publicidad")
                || ruta.equals("/contacto") || ruta.startsWith("/css/")
                || ruta.startsWith("/img/") || ruta.startsWith("/js/")
                || ruta.equals("/favicon.ico")) {
            return true;
        }

        Usuario usuario = usuarioSesionService.getUsuarioActual();
        if (usuario == null || !"Administrador".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login?acceso=denegado");
            return false;
        }

        return true;
    }
}
