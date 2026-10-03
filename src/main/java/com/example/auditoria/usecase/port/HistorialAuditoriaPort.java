package com.example.auditoria.usecase.port;

import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;

import java.util.List;

// Un solo puerto para la bitacora: escritura (append-only) y lectura cronologica.
public interface HistorialAuditoriaPort {
    void registrar(HallazgoId hallazgoId, EstadoHallazgo anterior, EstadoHallazgo nuevo, String motivo);
    List<CambioEstadoView> listarPorHallazgo(HallazgoId hallazgoId);
}
