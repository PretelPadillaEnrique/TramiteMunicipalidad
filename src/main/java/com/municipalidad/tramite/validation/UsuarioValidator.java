package com.municipalidad.tramite.validation;

import com.municipalidad.tramite.model.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Set;
import java.util.regex.Pattern;

@Component
public class UsuarioValidator implements Validator {

    private static final Pattern PERSON_NAME = Pattern.compile("[\\p{L}\\p{M} .'-]+");
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Set<String> USER_ROLES = Set.of("Administrador", "Usuario");

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario = (Usuario) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "usuario.nombre.required",
                "El nombre es obligatorio.");
        if (notBlank(usuario.getNombre())
                && (!PERSON_NAME.matcher(usuario.getNombre().trim()).matches()
                || usuario.getNombre().trim().length() < 2
                || usuario.getNombre().trim().length() > 255)) {
            errors.rejectValue("nombre", "usuario.nombre.invalid",
                    "Ingrese un nombre de 2 a 255 caracteres, usando letras y separadores habituales.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "correo", "usuario.correo.required",
                "El correo es obligatorio.");
        if (notBlank(usuario.getCorreo())
                && (usuario.getCorreo().length() > 255 || !EMAIL.matcher(usuario.getCorreo().trim()).matches())) {
            errors.rejectValue("correo", "usuario.correo.invalid",
                    "Ingrese un correo válido de hasta 255 caracteres.");
        }

        if (usuario.getId() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "usuario.password.required",
                    "La contraseña es obligatoria.");
            if (notBlank(usuario.getPassword())
                    && (usuario.getPassword().length() < 8 || usuario.getPassword().length() > 255)) {
                errors.rejectValue("password", "usuario.password.length",
                        "La contraseña debe tener entre 8 y 255 caracteres.");
            }
        }

        if (usuario.getRol() == null || !USER_ROLES.contains(usuario.getRol())) {
            errors.rejectValue("rol", "usuario.rol.invalid", "Seleccione un rol válido.");
        }
    }

    private boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }
}
