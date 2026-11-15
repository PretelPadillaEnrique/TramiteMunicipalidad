package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.service.AreaService;
import com.municipalidad.tramite.validation.AreaValidator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AreaController {

    private final AreaService areaService;
    private final AreaValidator validator;

    public AreaController(AreaService areaService, AreaValidator validator) {
        this.areaService = areaService;
        this.validator = validator;
    }

    @InitBinder("area")
    void initAreaBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/registrar-area")
    public String registrarArea(Model model) {
        model.addAttribute("area", new Area());
        return "registrar-area";
    }

    @PostMapping("/registrar-area")
    public String guardarArea(@Validated @ModelAttribute("area") Area area, BindingResult result) {
        if (result.hasErrors()) {
            return "registrar-area";
        }
        areaService.registrar(area);
        return "redirect:/listar-areas";
    }

    @GetMapping("/listar-areas")
    public String listarAreas(Model model) {
        model.addAttribute("areas", areaService.listar());
        return "listar-areas";
    }

    @GetMapping("/editar-area")
    public String editarArea(@RequestParam Long id, Model model) {
        model.addAttribute("area", areaService.buscarPorId(id));
        return "editar-area";
    }

    @PostMapping("/editar-area")
    public String guardarEdicionArea(@Validated @ModelAttribute("area") Area area, BindingResult result) {
        if (result.hasErrors()) {
            return "editar-area";
        }
        areaService.actualizar(area);
        return "redirect:/listar-areas";
    }

    @GetMapping("/desactivar-area")
    public String desactivarArea(@RequestParam Long id, Model model) {
        model.addAttribute("area", areaService.buscarPorId(id));
        return "desactivar-area";
    }

    @PostMapping("/desactivar-area")
    public String confirmarDesactivarArea(@ModelAttribute("area") Area area) {
        areaService.desactivar(area.getId());
        return "redirect:/listar-areas";
    }
}