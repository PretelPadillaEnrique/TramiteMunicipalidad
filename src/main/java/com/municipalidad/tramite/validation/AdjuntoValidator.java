package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Adjunto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AdjuntoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Adjunto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Adjunto adjunto = (Adjunto) target;
        String validationMessage = AdjuntoFileRules.validate(adjunto.getArchivo());
        if (validationMessage != null) {
            errors.rejectValue("archivo", "adjunto.archivo.invalid", validationMessage);
        }
    }
}
