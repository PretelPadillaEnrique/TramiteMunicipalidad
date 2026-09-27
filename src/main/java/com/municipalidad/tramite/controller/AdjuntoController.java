package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.service.AdjuntoService;
import com.municipalidad.tramite.service.ExpedienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdjuntoController {

    private final AdjuntoService adjuntoService;
    private final ExpedienteService expedienteService;

    public AdjuntoController(AdjuntoService adjuntoService, ExpedienteService expedienteService) {
        this.adjuntoService = adjuntoService;
        this.expedienteService = expedienteService;
    }

    @GetMapping("/adjuntar-documentos")
    public String verAdjuntos(@RequestParam Long id, Model model) {
        model.addAttribute("expediente", expedienteService.buscarPorId(id));
        model.addAttribute("adjuntos", adjuntoService.listarPorExpediente(id));
        return "adjuntar-documentos";
    }

    @PostMapping("/adjuntar-documentos")
    public String guardarAdjunto(@RequestParam Long idExpediente, @RequestParam String nombreArchivo) {
        adjuntoService.registrar(nombreArchivo, idExpediente);
        return "redirect:/adjuntar-documentos?id=" + idExpediente;
    }
}
