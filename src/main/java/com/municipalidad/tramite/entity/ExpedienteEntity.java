package com.municipalidad.tramite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "EXPEDIENTE")
public class ExpedienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folio")
    private String folio;

    @Column(name = "ciudadanoNombre")
    private String ciudadanoNombre;

    @Column(name = "ciudadanoDni")
    private String ciudadanoDni;

    @Column(name = "ciudadanoContacto")
    private String ciudadanoContacto;

    @Column(name = "fechaRegistro")
    private LocalDate fechaRegistro;

    @Column(name = "fechaLimite")
    private LocalDate fechaLimite;

    @Column(name = "motivoReprogramacion")
    private String motivoReprogramacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaActual_id")
    private AreaEntity areaActual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioRegistro_id")
    private UsuarioEntity usuarioRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioUltimoCambio_id")
    private UsuarioEntity usuarioUltimoCambio;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public AreaEntity getAreaActual() {
        return areaActual;
    }

    public void setAreaActual(AreaEntity areaActual) {
        this.areaActual = areaActual;
    }

    public UsuarioEntity getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(UsuarioEntity usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public UsuarioEntity getUsuarioUltimoCambio() {
        return usuarioUltimoCambio;
    }

    public void setUsuarioUltimoCambio(UsuarioEntity usuarioUltimoCambio) {
        this.usuarioUltimoCambio = usuarioUltimoCambio;
    }
}
