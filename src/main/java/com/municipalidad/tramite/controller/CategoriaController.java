package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Categoria;
import com.municipalidad.tramite.service.AreaService;
import com.municipalidad.tramite.service.CategoriaService;
import com.municipalidad.tramite.validation.CategoriaValidator;
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
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final AreaService areaService;
    private final CategoriaValidator validator;

    public CategoriaController(CategoriaService categoriaService, AreaService areaService,
                               CategoriaValidator validator) {
        this.categoriaService = categoriaService;
        this.areaService = areaService;
        this.validator = validator;
    }

    @InitBinder("categoria")
    void initCategoriaBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/registrar-categoria")
    public String registrarCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("areas", areaService.listarActivas());
        return "registrar-categoria";
    }

    @PostMapping("/registrar-categoria")
    public String guardarCategoria(@Validated @ModelAttribute("categoria") Categoria categoria, BindingResult result,
                                   @RequestParam(required = false) Long idArea, Model model) {
        if (idArea == null || idArea <= 0) {
            result.reject("categoria.area.required", "Debe seleccionar un área.");
        }
        model.addAttribute("selectedAreaId", idArea);
        if (result.hasErrors()) {
            model.addAttribute("areas", areaService.listarActivas());
            return "registrar-categoria";
        }
        categoriaService.registrar(categoria, idArea);
        return "redirect:/listar-categorias";
    }

    @GetMapping("/listar-categorias")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        return "listar-categorias";
    }

    @GetMapping("/editar-categoria")
    public String editarCategoria(@RequestParam Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        model.addAttribute("areas", areaService.listar());
        return "editar-categoria";
    }

    @PostMapping("/editar-categoria")
    public String guardarEdicionCategoria(@Validated @ModelAttribute("categoria") Categoria categoria,
                                          BindingResult result,
                                          @RequestParam(required = false) Long idArea, Model model) {
        if (idArea == null || idArea <= 0) {
            result.reject("categoria.area.required", "Debe seleccionar un área.");
        }
        model.addAttribute("selectedAreaId", idArea);
        if (result.hasErrors()) {
            model.addAttribute("areas", areaService.listar());
            return "editar-categoria";
        }
        categoriaService.actualizar(categoria, idArea);
        return "redirect:/listar-categorias";
    }

    @GetMapping("/desactivar-categoria")
    public String desactivarCategoria(@RequestParam Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        return "desactivar-categoria";
    }

    @PostMapping("/desactivar-categoria")
    public String confirmarDesactivarCategoria(@ModelAttribute("categoria") Categoria categoria) {
        categoriaService.desactivar(categoria.getId());
        return "redirect:/listar-categorias";
    }
}
