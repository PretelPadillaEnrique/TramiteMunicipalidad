package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Adjunto;
import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.model.Categoria;
import com.municipalidad.tramite.model.Expediente;
import com.municipalidad.tramite.model.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DummyDataService {

    private final List<Area> areas = new ArrayList<>();
    private final List<Categoria> categorias = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Expediente> expedientes = new ArrayList<>();
    private final List<Adjunto> adjuntos = new ArrayList<>();

    private final AtomicLong areaIdSeq = new AtomicLong(1);
    private final AtomicLong categoriaIdSeq = new AtomicLong(1);
    private final AtomicLong usuarioIdSeq = new AtomicLong(1);
    private final AtomicLong expedienteIdSeq = new AtomicLong(1);
    private final AtomicLong adjuntoIdSeq = new AtomicLong(1);

    @PostConstruct
    public void initDummyData() {
        // Areas de prueba
        Area obrasPublicas = registrarArea(new Area(null, "Obras Públicas", "Gestión de obras e infraestructura", "Activo"));
        Area rentas = registrarArea(new Area(null, "Rentas", "Administración tributaria municipal", "Activo"));
        registrarArea(new Area(null, "Desarrollo Urbano", "Licencias de edificación", "Activo"));

        // Categorias de prueba (ahora con el objeto Area, no texto suelto)
        Categoria licencia = registrarCategoria(new Categoria(null, "Licencia de funcionamiento", 15, rentas, "Activo"));
        registrarCategoria(new Categoria(null, "Permiso de construcción", 30, obrasPublicas, "Activo"));

        // Usuarios de prueba (ahora con el objeto Area, no texto suelto)
        Usuario recepcion = registrarUsuario(new Usuario(null, "María Perez", "mariap@munilosolivos.gob.pe", "123456", "Recepción", rentas, "Activo"));
        registrarUsuario(new Usuario(null, "Juan Pérez", "admin@munilosolivos.gob.pe", "123456", "Administrador", obrasPublicas, "Activo"));

        // Expedientes de prueba (con los campos que sostienen las reglas de negocio)
        Expediente exp1 = registrarExpediente(new Expediente(
                null, "EXP-2026-001",
                "Carlos Gómez", "74859632", "carlos@email.com",
                licencia, rentas, recepcion,
                LocalDate.of(2026, 8, 25), LocalDate.of(2026, 9, 9),
                "Pendiente"
        ));

        // Adjunto de prueba (la entidad que faltaba)
        registrarAdjunto(new Adjunto(null, "dni.pdf", LocalDate.of(2026, 8, 25), exp1));
    }

    public Area registrarArea(Area area) {
        area.setId(areaIdSeq.getAndIncrement());
        if (area.getEstado() == null) area.setEstado("Activo");
        areas.add(area);
        return area;
    }

    public Categoria registrarCategoria(Categoria categoria) {
        categoria.setId(categoriaIdSeq.getAndIncrement());
        if (categoria.getEstado() == null) categoria.setEstado("Activo");
        categorias.add(categoria);
        return categoria;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setId(usuarioIdSeq.getAndIncrement());
        if (usuario.getEstado() == null) usuario.setEstado("Activo");
        usuarios.add(usuario);
        return usuario;
    }

    public Expediente registrarExpediente(Expediente expediente) {
        expediente.setId(expedienteIdSeq.getAndIncrement());
        if (expediente.getFolio() == null) {
            expediente.setFolio("EXP-2026-00" + expediente.getId());
        }
        if (expediente.getFechaRegistro() == null) {
            expediente.setFechaRegistro(LocalDate.now());
        }
        if (expediente.getEstado() == null) {
            expediente.setEstado("Pendiente");
        }
        expedientes.add(expediente);
        return expediente;
    }

    public Adjunto registrarAdjunto(Adjunto adjunto) {
        adjunto.setId(adjuntoIdSeq.getAndIncrement());
        if (adjunto.getFechaCarga() == null) {
            adjunto.setFechaCarga(LocalDate.now());
        }
        adjuntos.add(adjunto);
        return adjunto;
    }

    public List<Area> getAreas() {
        return areas;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public List<Expediente> getExpedientes() {
        return expedientes;
    }
    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }
}