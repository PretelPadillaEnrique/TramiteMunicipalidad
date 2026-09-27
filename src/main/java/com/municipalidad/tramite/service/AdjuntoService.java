package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Adjunto;
import com.municipalidad.tramite.model.Expediente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdjuntoService {

    private final DummyDataService dummyDataService;
    private final ExpedienteService expedienteService;

    public AdjuntoService(DummyDataService dummyDataService, ExpedienteService expedienteService) {
        this.dummyDataService = dummyDataService;
        this.expedienteService = expedienteService;
    }

    public List<Adjunto> listarPorExpediente(Long idExpediente) {
        List<Adjunto> resultado = new ArrayList<>();
        for (Adjunto adjunto : dummyDataService.getAdjuntos()) {
            if (adjunto.getExpediente() != null && adjunto.getExpediente().getId().equals(idExpediente)) {
                resultado.add(adjunto);
            }
        }
        return resultado;
    }

    public void registrar(String nombreArchivo, Long idExpediente) {
        Expediente expediente = expedienteService.buscarPorId(idExpediente);
        Adjunto adjunto = new Adjunto(null, nombreArchivo, LocalDate.now(), expediente);
        dummyDataService.registrarAdjunto(adjunto);
    }
}
