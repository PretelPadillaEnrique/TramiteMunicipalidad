package com.municipalidad.tramite.controller;

import com.municipalidad.tramite.entity.ExpedienteEntity;
import com.municipalidad.tramite.repository.ExpedienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class MetricasController {

    private final ExpedienteRepository expedienteRepository;

    public MetricasController(ExpedienteRepository expedienteRepository) {
        this.expedienteRepository = expedienteRepository;
    }

    @GetMapping("/metricas")
    public String mostrarMetricas(Model model) {
        List<ExpedienteEntity> expedientes = expedienteRepository.findAll();
        List<String> semanas = new ArrayList<>();
        List<Integer> registrados = new ArrayList<>();
        List<Integer> pendientes = new ArrayList<>();
        List<Integer> aprobados = new ArrayList<>();
        List<Integer> observados = new ArrayList<>();
        List<Integer> rechazados = new ArrayList<>();

        LocalDate inicio = LocalDate.of(2026, 9, 15);
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("d MMM", new Locale("es", "PE"));

        // Cada punto representa una semana desde el 15 de septiembre hasta la fecha actual.
        for (LocalDate desde = inicio; !desde.isAfter(hoy); desde = desde.plusWeeks(1)) {
            LocalDate hasta = desde.plusWeeks(1).minusDays(1);
            if (hasta.isAfter(hoy)) hasta = hoy;
            semanas.add(desde.format(formato) + " - " + hasta.format(formato));

            int total = 0;
            int totalPendientes = 0;
            int totalAprobados = 0;
            int totalObservados = 0;
            int totalRechazados = 0;

            for (ExpedienteEntity expediente : expedientes) {
                LocalDate fecha = expediente.getFechaRegistro();
                if (fecha != null && !fecha.isBefore(desde) && !fecha.isAfter(hasta)) {
                    total++;
                    if ("Pendiente".equalsIgnoreCase(expediente.getEstado())) totalPendientes++;
                    if ("Aprobado".equalsIgnoreCase(expediente.getEstado())) totalAprobados++;
                    if ("Observado".equalsIgnoreCase(expediente.getEstado())) totalObservados++;
                    if ("Rechazado".equalsIgnoreCase(expediente.getEstado())) totalRechazados++;
                }
            }
            registrados.add(total);
            pendientes.add(totalPendientes);
            aprobados.add(totalAprobados);
            observados.add(totalObservados);
            rechazados.add(totalRechazados);
        }

        model.addAttribute("totalExpedientes", expedientes.size());
        model.addAttribute("totalPendientes", contarEstado(expedientes, "Pendiente"));
        model.addAttribute("totalAprobados", contarEstado(expedientes, "Aprobado"));
        model.addAttribute("totalObservados", contarEstado(expedientes, "Observado"));
        model.addAttribute("totalRechazados", contarEstado(expedientes, "Rechazado"));
        model.addAttribute("semanas", semanas);
        model.addAttribute("registrados", registrados);
        model.addAttribute("pendientes", pendientes);
        model.addAttribute("aprobados", aprobados);
        model.addAttribute("observados", observados);
        model.addAttribute("rechazados", rechazados);
        return "metricas";
    }

    private int contarEstado(List<ExpedienteEntity> expedientes, String estado) {
        int total = 0;
        for (ExpedienteEntity expediente : expedientes) {
            if (estado.equalsIgnoreCase(expediente.getEstado())) total++;
        }
        return total;
    }
}
