package com.municipalidad.tramite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaEstaticaController {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    @GetMapping("/publicidad")
    public String publicidad() {
        return "publicidad";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}