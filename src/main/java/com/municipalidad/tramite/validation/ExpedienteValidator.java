package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Expediente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ExpedienteValidator implements Validator {

    private static final Pattern PERSON_NAME = Pattern.compile("[\\p{L}\\p{M} .'-]+");
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern DNI = Pattern.compile("[0-9]{8}");

    @Override
    public boolean supports(Class<?> clazz) {
        return Expediente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Expediente expediente = (Expediente) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ciudadanoNombre", "expediente.nombre.required",
                "El nombre del ciudadano es obligatorio.");
        if (notBlank(expediente.getCiudadanoNombre())
                && (!PERSON_NAME.matcher(expediente.getCiudadanoNombre().trim()).matches()
                || expediente.getCiudadanoNombre().trim().length() < 2
                || expediente.getCiudadanoNombre().trim().length() > 255)) {
            errors.rejectValue("ciudadanoNombre", "expediente.nombre.invalid",
                    "Ingrese un nombre de 2 a 255 caracteres, usando letras y separadores habituales.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ciudadanoDni", "expediente.dni.required",
                "El DNI es obligatorio.");
        if (notBlank(expediente.getCiudadanoDni())
                && !DNI.matcher(expediente.getCiudadanoDni().trim()).matches()) {
            errors.rejectValue("ciudadanoDni", "expediente.dni.invalid",
                    "El DNI debe tener exactamente 8 dígitos.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ciudadanoContacto", "expediente.contacto.required",
                "El correo de contacto es obligatorio.");
        if (notBlank(expediente.getCiudadanoContacto())
                && (expediente.getCiudadanoContacto().length() > 255
                || !EMAIL.matcher(expediente.getCiudadanoContacto().trim()).matches())) {
            errors.rejectValue("ciudadanoContacto", "expediente.contacto.invalid",
                    "Ingrese un correo de contacto válido de hasta 255 caracteres.");
        }
    }

    private boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }
}
