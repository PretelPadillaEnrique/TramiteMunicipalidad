package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Expediente;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpedienteValidatorTest {

    private final ExpedienteValidator validator = new ExpedienteValidator();

    @Test
    void acceptsAnEightDigitDni() {
        Expediente expediente = expedienteConDni("12345678");
        BeanPropertyBindingResult errors = errorsFor(expediente);

        validator.validate(expediente, errors);

        assertFalse(errors.hasFieldErrors("ciudadanoDni"));
        assertFalse(errors.hasErrors());
    }

    @Test
    void rejectsDniWithOtherThanEightDigits() {
        Expediente expediente = expedienteConDni("1234567A");
        BeanPropertyBindingResult errors = errorsFor(expediente);

        validator.validate(expediente, errors);

        assertTrue(errors.hasFieldErrors("ciudadanoDni"));
    }

    private Expediente expedienteConDni(String dni) {
        Expediente expediente = new Expediente();
        expediente.setCiudadanoNombre("Ana Pérez");
        expediente.setCiudadanoDni(dni);
        expediente.setCiudadanoContacto("ana@example.com");
        return expediente;
    }

    private BeanPropertyBindingResult errorsFor(Object target) {
        return new BeanPropertyBindingResult(target, "target");
    }
}
