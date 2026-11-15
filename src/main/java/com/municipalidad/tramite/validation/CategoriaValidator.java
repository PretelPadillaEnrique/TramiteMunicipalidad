package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Categoria;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoriaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Categoria.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Categoria categoria = (Categoria) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "categoria.nombre.required",
                "El nombre de la categoría es obligatorio.");
        if (categoria.getNombre() != null && categoria.getNombre().length() > 255) {
            errors.rejectValue("nombre", "categoria.nombre.tooLong",
                    "El nombre no puede superar 255 caracteres.");
        }
        if (categoria.getPlazoAtencion() == null || categoria.getPlazoAtencion() <= 0) {
            errors.rejectValue("plazoAtencion", "categoria.plazo.positive",
                    "El plazo debe ser un número de días mayor que cero.");
        }
    }
}
