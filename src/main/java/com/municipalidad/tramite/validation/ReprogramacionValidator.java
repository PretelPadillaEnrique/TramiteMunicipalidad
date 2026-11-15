package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Expediente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ReprogramacionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Expediente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Expediente expediente = (Expediente) target;
        if (expediente.getId() == null || expediente.getId() <= 0) {
            errors.rejectValue("id", "reprogramacion.id.invalid", "El expediente seleccionado no es válido.");
        }

        String motivo = expediente.getMotivoReprogramacion();
        if (motivo == null || motivo.trim().length() < 10) {
            errors.rejectValue("motivoReprogramacion", "reprogramacion.motivo.short",
                    "El motivo debe tener al menos 10 caracteres.");
        } else if (motivo.trim().length() > 255) {
            errors.rejectValue("motivoReprogramacion", "reprogramacion.motivo.long",
                    "El motivo no puede superar 255 caracteres.");
        }

        LocalDate nuevaFecha = expediente.getFechaLimite();
        if (nuevaFecha == null || !nuevaFecha.isAfter(LocalDate.now())) {
            errors.rejectValue("fechaLimite", "reprogramacion.fecha.future",
                    "La nueva fecha debe ser posterior al día de hoy.");
        }
    }
}
