package com.municipalidad.tramite.model;

import java.time.LocalDate;

public class Adjunto {
    private Long id;
    private String nombreArchivo;
    private LocalDate fechaCarga;
    private Expediente expediente;

    public Adjunto() {}

    public Adjunto(Long id, String nombreArchivo, LocalDate fechaCarga, Expediente expediente) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.fechaCarga = fechaCarga;
        this.expediente = expediente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDate fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
}