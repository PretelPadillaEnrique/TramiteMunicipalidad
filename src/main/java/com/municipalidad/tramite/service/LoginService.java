package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final DummyDataService dummyDataService;

    public LoginService(DummyDataService dummyDataService) {
        this.dummyDataService = dummyDataService;
    }

    public Usuario validar(String correo, String password) {
        for (Usuario usuario : dummyDataService.getUsuarios()) {
            boolean coincideCorreo = usuario.getCorreo().equals(correo);
            boolean coincidePassword = usuario.getPassword().equals(password);
            boolean estaActivo = "Activo".equals(usuario.getEstado());
            if (coincideCorreo && coincidePassword && estaActivo) {
                return usuario;
            }
        }
        return null;
    }
}
