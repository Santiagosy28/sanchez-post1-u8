package com.example.auditoria.domain;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.PlanRemediacion;
import com.example.auditoria.domain.valueobject.Severidad;
import com.example.auditoria.domain.valueobject.TransicionInvalidaException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Prueba de dominio PURA: sin @SpringBootTest, sin frameworks.
class HallazgoAuditoriaTest {

    private HallazgoAuditoria nuevoHallazgo() {
        return new HallazgoAuditoria(HallazgoId.nuevo(), "Credenciales por defecto",
            "Servidor QA con credenciales del fabricante", "Infraestructura",
            Severidad.ALTA, LocalDate.of(2026, 8, 1));
    }

    private PlanRemediacion plan() {
        return new PlanRemediacion("Equipo Infra", LocalDate.of(2026, 8, 20), "Rotar credenciales");
    }

    @Test
    void nuevoHallazgoNaceAbierto() {
        assertEquals(EstadoHallazgo.ABIERTO, nuevoHallazgo().getEstado());
    }

    @Test
    void cicloDeVidaValidoAbiertoRemediacionCerradoReabierto() {
        HallazgoAuditoria h = nuevoHallazgo();
        assertEquals(EstadoHallazgo.ABIERTO, h.iniciarRemediacion(plan()));
        assertEquals(EstadoHallazgo.EN_REMEDIACION, h.getEstado());
        assertEquals(EstadoHallazgo.EN_REMEDIACION, h.cerrar());
        assertEquals(EstadoHallazgo.CERRADO, h.getEstado());
        assertNotNull(h.getFechaCierre());
        assertEquals(EstadoHallazgo.CERRADO, h.reabrir());
        assertEquals(EstadoHallazgo.REABIERTO, h.getEstado());
        assertNull(h.getFechaCierre());
    }

    @Test
    void noSePuedeCerrarUnHallazgoAbiertoSinPlan() {
        HallazgoAuditoria h = nuevoHallazgo();
        // cerrar() valida primero la ausencia de plan -> IllegalStateException.
        assertThrows(IllegalStateException.class, h::cerrar);
    }

    @Test
    void noSePuedeReabrirUnHallazgoQueSigueAbierto() {
        assertThrows(TransicionInvalidaException.class, nuevoHallazgo()::reabrir);
    }

    @Test
    void severidadEsUnaClasificacionSinReglasDeTransicion() {
        // Severidad no tiene maquina de estados: es solo una de cuatro categorias.
        assertEquals(4, Severidad.values().length);
    }
}
