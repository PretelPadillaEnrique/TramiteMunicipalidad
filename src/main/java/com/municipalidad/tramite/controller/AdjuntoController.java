package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Adjunto;
import com.municipalidad.tramite.service.AdjuntoService;
import com.municipalidad.tramite.service.ExpedienteService;
import com.municipalidad.tramite.validation.AdjuntoValidator;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Controller
public class AdjuntoController {

    private final AdjuntoService adjuntoService;
    private final ExpedienteService expedienteService;
    private final AdjuntoValidator validator;

    public AdjuntoController(AdjuntoService adjuntoService, ExpedienteService expedienteService,
                             AdjuntoValidator validator) {
        this.adjuntoService = adjuntoService;
        this.expedienteService = expedienteService;
        this.validator = validator;
    }

    @InitBinder("adjunto")
    void initAdjuntoBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/adjuntar-documentos")
    public String verAdjuntos(@RequestParam Long id,
                              @RequestParam(required = false) String cargado, Model model) {
        model.addAttribute("expediente", expedienteService.buscarPorId(id));
        model.addAttribute("adjuntos", adjuntoService.listarPorExpediente(id));
        model.addAttribute("adjunto", new Adjunto());
        if (cargado != null) {
            model.addAttribute("mensajeExito", "El archivo se adjuntó correctamente.");
        }
        return "adjuntar-documentos";
    }

    @PostMapping("/adjuntar-documentos")
    public String guardarAdjunto(@RequestParam Long idExpediente,
                                 @Validated @ModelAttribute("adjunto") Adjunto adjunto,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("expediente", expedienteService.buscarPorId(idExpediente));
            model.addAttribute("adjuntos", adjuntoService.listarPorExpediente(idExpediente));
            return "adjuntar-documentos";
        }
        try {
            adjuntoService.registrar(adjunto.getArchivo(), idExpediente);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            model.addAttribute("expediente", expedienteService.buscarPorId(idExpediente));
            model.addAttribute("adjuntos", adjuntoService.listarPorExpediente(idExpediente));
            model.addAttribute("errorCarga", exception.getMessage());
            return "adjuntar-documentos";
        }
        return "redirect:/adjuntar-documentos?id=" + idExpediente + "&cargado=si";
    }

    @GetMapping("/adjuntar-documentos/archivo/{id}")
    public ResponseEntity<Resource> descargarAdjunto(@PathVariable Long id) {
        AdjuntoService.DescargableAdjunto archivo = adjuntoService.obtenerArchivo(id);
        String extension = archivo.nombreArchivo().substring(archivo.nombreArchivo().lastIndexOf('.') + 1)
                .toLowerCase(Locale.ROOT);
        MediaType contentType = switch (extension) {
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(archivo.nombreArchivo(), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .body(archivo.recurso());
    }
}
