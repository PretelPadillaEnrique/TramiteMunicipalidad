package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Expediente;
import com.municipalidad.tramite.service.CategoriaService;
import com.municipalidad.tramite.service.ExpedienteService;
import com.municipalidad.tramite.validation.ExpedienteValidator;
import com.municipalidad.tramite.validation.ReprogramacionValidator;
import java.time.LocalDate;
import java.util.stream.Collectors;
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

@Controller
public class ExpedienteController {

    private final ExpedienteService expedienteService;
    private final CategoriaService categoriaService;
    private final ExpedienteValidator expedienteValidator;
    private final ReprogramacionValidator reprogramacionValidator;

    public ExpedienteController(ExpedienteService expedienteService, CategoriaService categoriaService,
                                ExpedienteValidator expedienteValidator,
                                ReprogramacionValidator reprogramacionValidator) {
        this.expedienteService = expedienteService;
        this.categoriaService = categoriaService;
        this.expedienteValidator = expedienteValidator;
        this.reprogramacionValidator = reprogramacionValidator;
    }

    @InitBinder("expediente")
    void initExpedienteBinder(WebDataBinder binder) {
        binder.addValidators(expedienteValidator);
    }

    @InitBinder("reprogramacion")
    void initReprogramacionBinder(WebDataBinder binder) {
        binder.addValidators(reprogramacionValidator);
    }

    @GetMapping("/gestionar-expediente")
    public String gestionarExpedientes(@RequestParam(required = false) String bloqueado,
                                       @RequestParam(required = false) String reabrirFallido, Model model) {
        if (bloqueado != null) {
            model.addAttribute("error", "No se pudo cambiar el estado. Verifique que el expediente esté pendiente y que su usuario tenga permisos. Puede hacerlo el administrador o un evaluador del área correspondiente.");
        } else if (reabrirFallido != null) {
            model.addAttribute("error", "No se pudo volver a revisión: el expediente no está observado.");
        }
        model.addAttribute("expediente", new Expediente());
        model.addAttribute("expedientes", expedienteService.listar());
        model.addAttribute("categorias", categoriaService.listarActivas());
        return "gestionar-expediente";
    }

    @PostMapping("/gestionar-expediente")
    public String registrarExpediente(@Validated @ModelAttribute("expediente") Expediente expediente,
                                      BindingResult result,
                                      @RequestParam(required = false) Long idCategoria,
                                      Model model) {
        if (idCategoria == null || idCategoria <= 0) {
            result.reject("expediente.categoria.required", "Debe seleccionar una categoría.");
        }
        model.addAttribute("selectedCategoryId", idCategoria);
        if (result.hasErrors()) {
            model.addAttribute("expedientes", expedienteService.listar());
            model.addAttribute("categorias", categoriaService.listarActivas());
            return "gestionar-expediente";
        }

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
    public String guardarEdicionExpediente(@Validated @ModelAttribute("expediente") Expediente expediente,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return "editar-expediente";
        }
        try {
            expedienteService.actualizarDatos(expediente);
        } catch (IllegalStateException e) {
            return "redirect:/gestionar-expediente?bloqueado=si";
        }
        return "redirect:/detalle-expediente?id=" + expediente.getId();
    }

    @PostMapping("/desactivar-expediente")
    public String desactivarExpediente(@RequestParam Long id) {
        expedienteService.desactivar(id);
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/reactivar-expediente")
    public String reactivarExpediente(@RequestParam Long id) {
        expedienteService.reactivar(id);
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/aprobar-expediente")
    public String aprobarExpediente(@RequestParam Long id) {
        if (!expedienteService.aprobar(id)) {
            return "redirect:/gestionar-expediente?bloqueado=si";
        }
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/observar-expediente")
    public String observarExpediente(@RequestParam Long id) {
        if (!expedienteService.observar(id)) {
            return "redirect:/gestionar-expediente?bloqueado=si";
        }
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/rechazar-expediente")
    public String rechazarExpediente(@RequestParam Long id) {
        if (!expedienteService.rechazar(id)) {
            return "redirect:/gestionar-expediente?bloqueado=si";
        }
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/volver-a-revision")
    public String volverARevision(@RequestParam Long id) {
        if (!expedienteService.volverARevision(id)) {
            return "redirect:/gestionar-expediente?reabrirFallido=si";
        }
        return "redirect:/gestionar-expediente";
    }

    @PostMapping("/reprogramar-expediente")
    public String guardarReprogramacion(@Validated @ModelAttribute("reprogramacion") Expediente expediente,
                                        BindingResult result, Model model) {
        String motivo = expediente.getMotivoReprogramacion();
        LocalDate nuevaFecha = expediente.getFechaLimite();
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(" ")));
            model.addAttribute("expediente", new Expediente());
            model.addAttribute("expedientes", expedienteService.listar());
            model.addAttribute("categorias", categoriaService.listarActivas());
            return "gestionar-expediente";
        }
        try {
            expedienteService.reprogramar(expediente.getId(), motivo, nuevaFecha);
        } catch (IllegalStateException e) {
            return "redirect:/gestionar-expediente?bloqueado=si";
        }
        return "redirect:/gestionar-expediente";
    }
}
