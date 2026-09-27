package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Categoria;
import com.municipalidad.tramite.model.Expediente;
import com.municipalidad.tramite.model.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExpedienteService {

    private final DummyDataService dummyDataService;

    public ExpedienteService(DummyDataService dummyDataService) {
        this.dummyDataService = dummyDataService;
    }

    public List<Expediente> listar() {
        return dummyDataService.getExpedientes();
    }

    public Expediente buscarPorId(Long id) {
        for (Expediente expediente : dummyDataService.getExpedientes()) {
            if (expediente.getId().equals(id)) {
                return expediente;
            }
        }
        return null;
    }

    private Categoria buscarCategoriaPorId(Long id) {
        for (Categoria categoria : dummyDataService.getCategorias()) {
            if (categoria.getId().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioRecepcion() {
        for (Usuario usuario : dummyDataService.getUsuarios()) {
            if ("Recepción".equals(usuario.getRol())) {
                return usuario;
            }
        }
        return null;
    }

    public void registrar(Expediente expediente, Long idCategoria) {
        Categoria categoria = buscarCategoriaPorId(idCategoria);
        Usuario recepcion = buscarUsuarioRecepcion();
        LocalDate hoy = LocalDate.now();

        expediente.setCategoria(categoria);
        expediente.setAreaActual(categoria != null ? categoria.getArea() : null);
        expediente.setUsuarioRegistro(recepcion);
        expediente.setUsuarioUltimoCambio(recepcion);
        expediente.setFechaRegistro(hoy);
        expediente.setFechaLimite(categoria != null ? hoy.plusDays(categoria.getPlazoAtencion()) : null);
        expediente.setEstado("Pendiente");

        dummyDataService.registrarExpediente(expediente);
    }

    private Usuario buscarUsuarioEvaluador() {
        for (Usuario usuario : dummyDataService.getUsuarios()) {
            if ("Usuario".equals(usuario.getRol())) {
                return usuario;
            }
        }
        return null;
    }

    public void aprobar(Long id) {
        Expediente expediente = buscarPorId(id);
        if (expediente != null) {
            expediente.setEstado("Aprobado");
            expediente.setUsuarioUltimoCambio(buscarUsuarioEvaluador());
        }
    }

    public void observar(Long id) {
        Expediente expediente = buscarPorId(id);
        if (expediente != null) {
            expediente.setEstado("Observado");
            expediente.setUsuarioUltimoCambio(buscarUsuarioEvaluador());
        }
    }

    public void rechazar(Long id) {
        Expediente expediente = buscarPorId(id);
        if (expediente != null) {
            expediente.setEstado("Rechazado");
            expediente.setUsuarioUltimoCambio(buscarUsuarioEvaluador());
        }
    }

    public void reprogramar(Long id, String motivo, LocalDate nuevaFecha) {
        Expediente expediente = buscarPorId(id);
        if (expediente != null) {
            expediente.setMotivoReprogramacion(motivo);
            expediente.setFechaLimite(nuevaFecha);
            expediente.setUsuarioUltimoCambio(buscarUsuarioEvaluador());
        }
    }

    public void volverARevision(Long id) {
        Expediente expediente = buscarPorId(id);
        if (expediente != null) {
            expediente.setEstado("Pendiente");
            expediente.setUsuarioUltimoCambio(buscarUsuarioEvaluador());
        }
    }

    public void actualizarDatos(Expediente datos) {
        Expediente expediente = buscarPorId(datos.getId());
        if (expediente != null) {
            expediente.setCiudadanoNombre(datos.getCiudadanoNombre());
            expediente.setCiudadanoDni(datos.getCiudadanoDni());
            expediente.setCiudadanoContacto(datos.getCiudadanoContacto());
        }
    }
}
