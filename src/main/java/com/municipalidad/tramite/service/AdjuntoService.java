package com.municipalidad.tramite.service;

import com.municipalidad.tramite.adapter.AdjuntoAdapter;
import com.municipalidad.tramite.entity.AdjuntoEntity;
import com.municipalidad.tramite.entity.ExpedienteEntity;
import com.municipalidad.tramite.model.Adjunto;
import com.municipalidad.tramite.repository.AdjuntoRepository;
import com.municipalidad.tramite.repository.ExpedienteRepository;
import com.municipalidad.tramite.validation.AdjuntoFileRules;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdjuntoService {

    private final AdjuntoRepository adjuntoRepository;
    private final ExpedienteRepository expedienteRepository;
    private final AdjuntoAdapter adjuntoAdapter;

    public AdjuntoService(AdjuntoRepository adjuntoRepository,
                          ExpedienteRepository expedienteRepository,
                          AdjuntoAdapter adjuntoAdapter) {
        this.adjuntoRepository = adjuntoRepository;
        this.expedienteRepository = expedienteRepository;
        this.adjuntoAdapter = adjuntoAdapter;
    }

    public List<Adjunto> listarPorExpediente(Long idExpediente) {
        return adjuntoRepository.findByExpediente_Id(idExpediente).stream()
                .map(adjuntoAdapter::toModel)
                .collect(Collectors.toList());
    }

    public void registrar(MultipartFile archivo, Long idExpediente) {
        String validationMessage = AdjuntoFileRules.validate(archivo);
        if (validationMessage != null) {
            throw new IllegalArgumentException(validationMessage);
        }
        ExpedienteEntity expediente = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new IllegalArgumentException("No existe el expediente " + idExpediente));

        String originalName = AdjuntoFileRules.originalFileName(archivo.getOriginalFilename());
        byte[] contenido;
        try {
            contenido = archivo.getBytes();
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer el archivo adjunto", exception);
        }

        AdjuntoEntity entity = new AdjuntoEntity();
        entity.setNombreArchivo(originalName);
        entity.setContenido(contenido);
        entity.setFechaCarga(LocalDate.now());
        entity.setExpediente(expediente);
        adjuntoRepository.save(entity);
    }

    public DescargableAdjunto obtenerArchivo(Long id) {
        AdjuntoEntity adjunto = adjuntoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el adjunto " + id));
        return new DescargableAdjunto(new ByteArrayResource(adjunto.getContenido()), adjunto.getNombreArchivo());
    }

    public record DescargableAdjunto(Resource recurso, String nombreArchivo) {}

    public void actualizar(Long id, String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.isBlank() || nombreArchivo.trim().length() > 255) {
            throw new IllegalArgumentException("El nombre del archivo debe tener entre 1 y 255 caracteres");
        }
        AdjuntoEntity entity = adjuntoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el adjunto " + id));
        entity.setNombreArchivo(nombreArchivo.trim());
        adjuntoRepository.save(entity);
    }
}
