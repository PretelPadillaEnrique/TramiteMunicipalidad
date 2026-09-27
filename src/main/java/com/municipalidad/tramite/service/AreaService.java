package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Area;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final DummyDataService dummyDataService;

    public AreaService(DummyDataService dummyDataService) {
        this.dummyDataService = dummyDataService;
    }

    public List<Area> listar() {
        return dummyDataService.getAreas();
    }

    public Area buscarPorId(Long id) {
        for (Area area : dummyDataService.getAreas()) {
            if (area.getId().equals(id)) {
                return area;
            }
        }
        return null;
    }

    public void registrar(Area area) {
        area.setEstado("Activo");
        dummyDataService.registrarArea(area);
    }

    public void actualizar(Area datos) {
        Area area = buscarPorId(datos.getId());
        if (area != null) {
            area.setNombre(datos.getNombre());
            area.setDescripcion(datos.getDescripcion());
        }
    }

    public void desactivar(Long id) {
        Area area = buscarPorId(id);
        if (area != null) {
            area.setEstado("Inactivo");
        }
    }
}
