package com.municipalidad.tramite.model;

public class Usuario {
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Area area;
    private String estado;

    public Usuario() {}

    public Usuario(Long id, String nombre, String correo, String password, String rol, Area area, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
