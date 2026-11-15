package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.service.AreaService;
import com.municipalidad.tramite.service.UsuarioService;
import com.municipalidad.tramite.validation.UsuarioValidator;
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
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AreaService areaService;
    private final UsuarioValidator validator;

    public UsuarioController(UsuarioService usuarioService, AreaService areaService,
                             UsuarioValidator validator) {
        this.usuarioService = usuarioService;
        this.areaService = areaService;
        this.validator = validator;
    }

    @InitBinder("usuario")
    void initUsuarioBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/registrar-usuario")
    public String registrarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("areas", areaService.listarActivas());
        return "registrar-usuario";
    }

    @PostMapping("/registrar-usuario")
    public String guardarUsuario(@Validated @ModelAttribute("usuario") Usuario usuario, BindingResult result,
                                 @RequestParam(required = false) Long idArea, Model model) {
        if (idArea == null || idArea <= 0) {
            result.reject("usuario.area.required", "Debe seleccionar un área.");
        }
        model.addAttribute("selectedAreaId", idArea);
        if (result.hasErrors()) {
            model.addAttribute("areas", areaService.listarActivas());
            return "registrar-usuario";
        }
        usuarioService.registrar(usuario, idArea);
        return "redirect:/listar-usuarios";
    }

    @GetMapping("/listar-usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "listar-usuarios";
    }

    @GetMapping("/editar-usuario")
    public String editarUsuario(@RequestParam Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        model.addAttribute("areas", areaService.listar());
        return "editar-usuario";
    }

    @PostMapping("/editar-usuario")
    public String guardarEdicionUsuario(@Validated @ModelAttribute("usuario") Usuario usuario,
                                        BindingResult result,
                                        @RequestParam(required = false) Long idArea, Model model) {
        if (idArea == null || idArea <= 0) {
            result.reject("usuario.area.required", "Debe seleccionar un área.");
        }
        model.addAttribute("selectedAreaId", idArea);
        if (result.hasErrors()) {
            model.addAttribute("areas", areaService.listar());
            return "editar-usuario";
        }
        usuarioService.actualizar(usuario, idArea);
        return "redirect:/listar-usuarios";
    }

    @GetMapping("/desactivar-usuario")
    public String desactivarUsuario(@RequestParam Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        return "desactivar-usuario";
    }

    @PostMapping("/desactivar-usuario")
    public String confirmarDesactivarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.desactivar(usuario.getId());
        return "redirect:/listar-usuarios";
    }
}
