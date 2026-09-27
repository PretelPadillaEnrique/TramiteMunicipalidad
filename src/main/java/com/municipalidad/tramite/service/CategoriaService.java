package com.municipalidad.tramite.service;

import com.municipalidad.tramite.model.Area;
import com.municipalidad.tramite.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final DummyDataService dummyDataService;

    public CategoriaService(DummyDataService dummyDataService) {
        this.dummyDataService = dummyDataService;
    }

    public List<Categoria> listar() {
        return dummyDataService.getCategorias();
    }

    public Categoria buscarPorId(Long id) {
        for (Categoria categoria : dummyDataService.getCategorias()) {
            if (categoria.getId().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

    private Area buscarAreaPorId(Long idArea) {
        for (Area area : dummyDataService.getAreas()) {
            if (area.getId().equals(idArea)) {
                return area;
            }
        }
        return null;
    }

    public void registrar(Categoria categoria, Long idArea) {
        categoria.setArea(buscarAreaPorId(idArea));
        categoria.setEstado("Activo");
        dummyDataService.registrarCategoria(categoria);
    }

    public void actualizar(Categoria datos, Long idArea) {
        Categoria categoria = buscarPorId(datos.getId());
        if (categoria != null) {
            categoria.setNombre(datos.getNombre());
            categoria.setPlazoAtencion(datos.getPlazoAtencion());
            categoria.setArea(buscarAreaPorId(idArea));
        }
    }

    public void desactivar(Long id) {
        Categoria categoria = buscarPorId(id);
        if (categoria != null) {
            categoria.setEstado("Inactivo");
        }
    }
}
