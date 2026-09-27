package com.municipalidad.tramite.model;

import java.time.LocalDate;

public class Expediente {
    private Long id;
    private String folio;
    private String ciudadanoNombre;
    private String ciudadanoDni;
    private String ciudadanoContacto;
    private Categoria categoria;
    private Area areaActual;
    private Usuario usuarioRegistro;
    private Usuario usuarioUltimoCambio;
    private LocalDate fechaRegistro;
    private LocalDate fechaLimite;
    private String motivoReprogramacion;
    private String estado;

    public Expediente() {}

    public Expediente(Long id, String folio, String ciudadanoNombre, String ciudadanoDni, String ciudadanoContacto,
                      Categoria categoria, Area areaActual, Usuario usuarioRegistro,
                      LocalDate fechaRegistro, LocalDate fechaLimite, String estado) {
        this.id = id;
        this.folio = folio;
        this.ciudadanoNombre = ciudadanoNombre;
        this.ciudadanoDni = ciudadanoDni;
        this.ciudadanoContacto = ciudadanoContacto;
        this.categoria = categoria;
        this.areaActual = areaActual;
        this.usuarioRegistro = usuarioRegistro;
        this.usuarioUltimoCambio = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCiudadanoNombre() {
        return ciudadanoNombre;
    }

    public void setCiudadanoNombre(String ciudadanoNombre) {
        this.ciudadanoNombre = ciudadanoNombre;
    }

    public String getCiudadanoDni() {
        return ciudadanoDni;
    }

    public void setCiudadanoDni(String ciudadanoDni) {
        this.ciudadanoDni = ciudadanoDni;
    }

    public String getCiudadanoContacto() {
        return ciudadanoContacto;
    }

    public void setCiudadanoContacto(String ciudadanoContacto) {
        this.ciudadanoContacto = ciudadanoContacto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Area getAreaActual() {
        return areaActual;
    }

    public void setAreaActual(Area areaActual) {
        this.areaActual = areaActual;
    }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Usuario getUsuarioUltimoCambio() {
        return usuarioUltimoCambio;
    }

    public void setUsuarioUltimoCambio(Usuario usuarioUltimoCambio) {
        this.usuarioUltimoCambio = usuarioUltimoCambio;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getMotivoReprogramacion() {
        return motivoReprogramacion;
    }

    public void setMotivoReprogramacion(String motivoReprogramacion) {
        this.motivoReprogramacion = motivoReprogramacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
