package com.example.auditoria.usecase;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.PlanRemediacion;

import java.time.LocalDate;

// Modelo de salida del caso de uso de consulta (output boundary del circulo
// Use Cases). Es Java puro: no expone la entidad de dominio ni tipos de framework.
public record HallazgoResponse(
    String id,
    String titulo,
    String descripcion,
    String areaResponsable,
    String severidad,
    String estado,
    LocalDate fechaDeteccion,
    LocalDate fechaCierre,
    String planResponsable,
    LocalDate planFechaLimite,
    String planNotas
) {
    public static HallazgoResponse desde(HallazgoAuditoria h) {
        PlanRemediacion plan = h.getPlanRemediacion();
        return new HallazgoResponse(
            h.getId().toString(), h.getTitulo(), h.getDescripcion(), h.getAreaResponsable(),
            h.getSeveridad() != null ? h.getSeveridad().name() : null,
            h.getEstado().name(), h.getFechaDeteccion(), h.getFechaCierre(),
            plan != null ? plan.responsable() : null,
            plan != null ? plan.fechaLimite() : null,
            plan != null ? plan.notas() : null);
    }
}
