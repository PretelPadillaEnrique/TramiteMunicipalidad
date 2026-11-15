package com.municipalidad.tramite.service;

import com.municipalidad.tramite.adapter.ExpedienteAdapter;
import com.municipalidad.tramite.entity.CategoriaEntity;
import com.municipalidad.tramite.entity.ExpedienteEntity;
import com.municipalidad.tramite.entity.UsuarioEntity;
import com.municipalidad.tramite.model.Expediente;
import com.municipalidad.tramite.model.Usuario;
import com.municipalidad.tramite.repository.AdjuntoRepository;
import com.municipalidad.tramite.repository.CategoriaRepository;
import com.municipalidad.tramite.repository.ExpedienteRepository;
import com.municipalidad.tramite.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AdjuntoRepository adjuntoRepository;
    private final ExpedienteAdapter expedienteAdapter;
    private final UsuarioSesionService usuarioSesionService;

    public ExpedienteService(ExpedienteRepository expedienteRepository,
                             CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository,
                             AdjuntoRepository adjuntoRepository,
                             ExpedienteAdapter expedienteAdapter,
                             UsuarioSesionService usuarioSesionService) {
        this.expedienteRepository = expedienteRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.adjuntoRepository = adjuntoRepository;
        this.expedienteAdapter = expedienteAdapter;
        this.usuarioSesionService = usuarioSesionService;
    }

    public List<Expediente> listar() {
        verificarVencimientos();
        return expedienteRepository.findAll().stream()
                .map(expedienteAdapter::toModel)
                .collect(Collectors.toList());
    }

    public Expediente buscarPorId(Long id) {
        return expedienteRepository.findById(id)
                .map(expedienteAdapter::toModel)
                .orElse(null);
    }

    public Expediente buscarPorFolio(String folio) {
        return expedienteRepository.findByFolio(folio)
                .map(expedienteAdapter::toModel)
                .orElse(null);
    }

    public void registrar(Expediente expediente, Long idCategoria) {
        CategoriaEntity categoria = categoriaRepository.findById(idCategoria)
                .filter(entity -> "Activo".equals(entity.getEstado()))
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe o está inactiva"));
        Usuario actual = usuarioSesionService.getUsuarioActual();
        if (actual == null) {
            throw new IllegalStateException("Debe iniciar sesión para registrar un expediente");
        }
        UsuarioEntity registrador = usuarioRepository.findById(actual.getId())
                .filter(entity -> "Activo".equals(entity.getEstado()))
                .orElseThrow(() -> new IllegalStateException("El usuario de la sesión no existe o está inactivo"));

        LocalDate hoy = LocalDate.now();
        ExpedienteEntity entity = expedienteAdapter.toEntity(expediente);
        entity.setId(null);
        entity.setCategoria(categoria);
        entity.setAreaActual(categoria.getArea());
        entity.setUsuarioRegistro(registrador);
        entity.setUsuarioUltimoCambio(registrador);
        entity.setFechaRegistro(hoy);
        entity.setFechaLimite(hoy.plusDays(categoria.getPlazoAtencion()));
        entity.setEstado("Pendiente");
        entity.setActivo(true);
        ExpedienteEntity guardado = expedienteRepository.save(entity);
        guardado.setFolio(String.format("EXP-%d-%03d", hoy.getYear(), guardado.getId()));
        expedienteRepository.save(guardado);
    }

    private boolean mismaArea(ExpedienteEntity expediente, UsuarioEntity evaluador) {
        return expediente.getAreaActual() != null
                && evaluador.getArea() != null
                && expediente.getAreaActual().getId().equals(evaluador.getArea().getId());
    }

    private boolean puedeEvaluar(ExpedienteEntity expediente, UsuarioEntity usuario) {
        if (usuario == null || !"Activo".equals(usuario.getEstado())) {
            return false;
        }

        if ("Administrador".equals(usuario.getRol())) {
            return true;
        }

        return "Usuario".equals(usuario.getRol()) && mismaArea(expediente, usuario);
    }

    private boolean decidir(Long id, String decision) {
        ExpedienteEntity expediente = expedienteRepository.findById(id).orElse(null);
        Usuario actual = usuarioSesionService.getUsuarioActual();
        if (expediente == null || !expediente.isActivo() || !"Pendiente".equals(expediente.getEstado()) || actual == null) {
            return false;
        }
        UsuarioEntity evaluador = usuarioRepository.findById(actual.getId()).orElse(null);
        if (!puedeEvaluar(expediente, evaluador)) {
            return false;
        }
        if ("Aprobado".equals(decision) && adjuntoRepository.countByExpediente_Id(id) == 0) {
            return false;
        }
        expediente.setEstado(decision);
        expediente.setUsuarioUltimoCambio(evaluador);
        expedienteRepository.save(expediente);
        return true;
    }

    public boolean aprobar(Long id) {
        return decidir(id, "Aprobado");
    }

    public boolean observar(Long id) {
        return decidir(id, "Observado");
    }

    public boolean rechazar(Long id) {
        return decidir(id, "Rechazado");
    }

    public void reprogramar(Long id, String motivo, LocalDate nuevaFecha) {
        boolean motivoValido = motivo != null
                && motivo.trim().length() >= 10
                && motivo.trim().length() <= 255;
        if (!motivoValido || nuevaFecha == null || !nuevaFecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Indique un motivo de al menos 10 caracteres y una fecha futura");
        }
        ExpedienteEntity expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el expediente " + id));
        if (!expediente.isActivo()) {
            throw new IllegalStateException("El expediente está inactivo y no puede reprogramarse");
        }
        Usuario actual = usuarioSesionService.getUsuarioActual();
        UsuarioEntity evaluador = actual == null ? null : usuarioRepository.findById(actual.getId()).orElse(null);
        if (!puedeEvaluar(expediente, evaluador)) {
            throw new IllegalStateException("Solo el administrador o un evaluador del área correspondiente puede reprogramar el expediente");
        }
        expediente.setMotivoReprogramacion(motivo.trim());
        expediente.setFechaLimite(nuevaFecha);
        expediente.setEstado("Pendiente");
        expediente.setUsuarioUltimoCambio(usuarioQueActua());
        expedienteRepository.save(expediente);
    }

    public boolean volverARevision(Long id) {
        ExpedienteEntity expediente = expedienteRepository.findById(id).orElse(null);
        if (expediente == null || !expediente.isActivo() || !"Observado".equals(expediente.getEstado())) {
            return false;
        }
        Usuario actual = usuarioSesionService.getUsuarioActual();
        UsuarioEntity evaluador = actual == null ? null : usuarioRepository.findById(actual.getId()).orElse(null);
        if (!puedeEvaluar(expediente, evaluador)) {
            return false;
        }
        if (expediente.getFechaLimite() == null || expediente.getFechaLimite().isBefore(LocalDate.now())) {
            Integer plazoAtencion = expediente.getCategoria() == null
                    ? null
                    : expediente.getCategoria().getPlazoAtencion();
            if (plazoAtencion == null || plazoAtencion <= 0) {
                throw new IllegalStateException("La categoría del expediente no tiene un plazo de atención válido");
            }
            expediente.setFechaLimite(LocalDate.now().plusDays(plazoAtencion));
        }
        expediente.setEstado("Pendiente");
        expediente.setUsuarioUltimoCambio(usuarioQueActua());
        expedienteRepository.save(expediente);
        return true;
    }

    public void actualizarDatos(Expediente datos) {
        ExpedienteEntity entity = expedienteRepository.findById(datos.getId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el expediente " + datos.getId()));
        Usuario actual = usuarioSesionService.getUsuarioActual();
        UsuarioEntity usuario = actual == null ? null : usuarioRepository.findById(actual.getId()).orElse(null);
        if (!entity.isActivo() || !puedeEvaluar(entity, usuario)) {
            throw new IllegalStateException("No tiene permisos para editar este expediente o está inactivo");
        }
        entity.setCiudadanoNombre(datos.getCiudadanoNombre());
        entity.setCiudadanoDni(datos.getCiudadanoDni());
        entity.setCiudadanoContacto(datos.getCiudadanoContacto());
        expedienteRepository.save(entity);
    }

    public void desactivar(Long id) {
        ExpedienteEntity expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el expediente " + id));
        if (!expediente.isActivo()) {
            return;
        }
        expediente.setActivo(false);
        expediente.setUsuarioUltimoCambio(usuarioQueActua());
        expedienteRepository.save(expediente);
    }

    public void reactivar(Long id) {
        ExpedienteEntity expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el expediente " + id));
        expediente.setActivo(true);
        expediente.setUsuarioUltimoCambio(usuarioQueActua());
        expedienteRepository.save(expediente);
    }

    private UsuarioEntity usuarioQueActua() {
        Usuario actual = usuarioSesionService.getUsuarioActual();
        if (actual != null) {
            return usuarioRepository.findById(actual.getId()).orElse(null);
        }
        return usuarioRepository.findFirstByRolAndEstado("Usuario", "Activo").orElse(null);
    }

    private void verificarVencimientos() {
        List<ExpedienteEntity> vencidos = expedienteRepository
                .findByEstadoAndFechaLimiteBefore("Pendiente", LocalDate.now());
        for (ExpedienteEntity expediente : vencidos) {
            expediente.setEstado("Observado");
            expediente.setUsuarioUltimoCambio(null);
        }
        expedienteRepository.saveAll(vencidos);
    }
}
