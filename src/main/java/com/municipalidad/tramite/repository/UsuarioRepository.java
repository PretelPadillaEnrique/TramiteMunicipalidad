package com.municipalidad.tramite.repository;

import com.municipalidad.tramite.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreoAndPasswordAndEstado(String correo, String password, String estado);
    Optional<UsuarioEntity> findFirstByRol(String rol);
    Optional<UsuarioEntity> findFirstByRolAndEstado(String rol, String estado);
}
