package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Categoria;
import com.municipalidad.tramite.service.AreaService;
import com.municipalidad.tramite.service.CategoriaService;
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

    public CategoriaController(CategoriaService categoriaService, AreaService areaService) {
        this.categoriaService = categoriaService;
        this.areaService = areaService;
    }

    @GetMapping("/registrar-categoria")
    public String registrarCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("areas", areaService.listar());
        return "registrar-categoria";
    }

    @PostMapping("/registrar-categoria")
    public String guardarCategoria(@ModelAttribute("categoria") Categoria categoria, @RequestParam Long idArea) {
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
    public String guardarEdicionCategoria(@ModelAttribute("categoria") Categoria categoria, @RequestParam Long idArea) {
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
