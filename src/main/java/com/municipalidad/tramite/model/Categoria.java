package com.municipalidad.tramite.model;

public class Categoria {
    private Long id;
    private String nombre;
    private Integer plazoAtencion;
    private Area area;
    private String estado;

    public Categoria() {}

    public Categoria(Long id, String nombre, Integer plazoAtencion, Area area, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.plazoAtencion = plazoAtencion;
        this.area = area;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPlazoAtencion() {
        return plazoAtencion;
    }

    public void setPlazoAtencion(Integer plazoAtencion) {
        this.plazoAtencion = plazoAtencion;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}