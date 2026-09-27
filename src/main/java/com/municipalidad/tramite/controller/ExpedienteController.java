package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Expediente;
import com.municipalidad.tramite.service.CategoriaService;
import com.municipalidad.tramite.service.ExpedienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpedienteController {

    private final ExpedienteService expedienteService;
    private final CategoriaService categoriaService;

    public ExpedienteController(ExpedienteService expedienteService, CategoriaService categoriaService) {
        this.expedienteService = expedienteService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/gestionar-expediente")
    public String gestionarExpedientes(Model model) {
        model.addAttribute("expediente", new Expediente());
        model.addAttribute("expedientes", expedienteService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        return "gestionar-expediente";
    }

    @PostMapping("/gestionar-expediente")
    public String registrarExpediente(@ModelAttribute("expediente") Expediente expediente, @RequestParam Long idCategoria) {
        expedienteService.registrar(expediente, idCategoria);
        return "redirect:/gestionar-expediente";
    }

    @GetMapping("/buscar-expediente")
    public String buscarExpediente(Model model) {
        model.addAttribute("expedientes", expedienteService.listar());
        return "buscar-expediente";
    }

    @GetMapping("/detalle-expediente")
    public String verDetalle(@RequestParam Long id, Model model) {
        model.addAttribute("expediente", expedienteService.buscarPorId(id));
        return "detalle-expediente";
    }

    @GetMapping("/editar-expediente")
    public String editarExpediente(@RequestParam Long id, Model model) {
        model.addAttribute("expediente", expedienteService.buscarPorId(id));
        return "editar-expediente";
    }

    @PostMapping("/editar-expediente")
    public String guardarEdicionExpediente(@ModelAttribute("expediente") Expediente expediente) {
        expedienteService.actualizarDatos(expediente);
        return "redirect:/detalle-expediente?id=" + expediente.getId();
    }

    @PostMapping("/aprobar-expediente")
    public String aprobarExpediente(@RequestParam Long id) {
        expedienteService.aprobar(id);
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/observar-expediente")
    public String observarExpediente(@RequestParam Long id) {
        expedienteService.observar(id);
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/rechazar-expediente")
    public String rechazarExpediente(@RequestParam Long id) {
        expedienteService.rechazar(id);
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/volver-a-revision")
    public String volverARevision(@RequestParam Long id) {
        expedienteService.volverARevision(id);
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/reprogramar-expediente")
    public String guardarReprogramacion(@RequestParam Long id, @RequestParam String motivo,
                                        @RequestParam java.time.LocalDate nuevaFecha) {
        expedienteService.reprogramar(id, motivo, nuevaFecha);
        return "redirect:/gestionar-expediente";
    }
}