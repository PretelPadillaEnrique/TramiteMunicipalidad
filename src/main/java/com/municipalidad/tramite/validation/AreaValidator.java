package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Area;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AreaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Area.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Area area = (Area) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "area.nombre.required",
                "El nombre del área es obligatorio.");
        rejectTooLong(errors, "nombre", area.getNombre(), "El nombre no puede superar 255 caracteres.");
        rejectTooLong(errors, "descripcion", area.getDescripcion(), "La descripción no puede superar 255 caracteres.");
    }

    private void rejectTooLong(Errors errors, String field, String value, String message) {
        if (value != null && value.length() > 255) {
            errors.rejectValue(field, field + ".tooLong", message);
        }
    }
}
