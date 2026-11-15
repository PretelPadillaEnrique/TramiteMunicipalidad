package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String acceso, Model model) {
        loginService.cerrarSesion();
        if (acceso != null) {
            model.addAttribute("error", "Debe iniciar sesión con una cuenta de administrador.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, @RequestParam String password, Model model) {
        Usuario usuario = loginService.validar(correo, password);
        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }
        if (!"Administrador".equals(usuario.getRol())) {
            loginService.cerrarSesion();
            model.addAttribute("error", "Este sistema está habilitado para el administrador.");
            return "login";
        }
        return "redirect:/gestionar-expediente";
    }
}
