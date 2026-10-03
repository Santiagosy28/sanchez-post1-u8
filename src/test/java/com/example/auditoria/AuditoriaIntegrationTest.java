package com.example.auditoria;

import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.Severidad;
import com.example.auditoria.usecase.*;
import com.example.auditoria.usecase.port.CambioEstadoView;
import com.example.auditoria.usecase.port.DashboardAuditoriaView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuditoriaIntegrationTest {

    @Autowired private RegistrarHallazgoUseCase registrar;
    @Autowired private IniciarRemediacionUseCase iniciar;
    @Autowired private CerrarHallazgoUseCase cerrar;
    @Autowired private ConsultarHistorialUseCase historial;
    @Autowired private ObtenerDashboardAuditoriaUseCase dashboard;

    @Test
    void cicloCompletoRegistraHistorialYAlimentaElDashboard() {
        HallazgoId id = registrar.ejecutar("Credenciales", "QA", "Infraestructura",
            Severidad.ALTA, LocalDate.of(2026, 8, 1));
        iniciar.ejecutar(id, "Infra", LocalDate.of(2026, 8, 20), "Rotar");
        cerrar.ejecutar(id);

        // Historial append-only: exactamente 2 transiciones en orden cronologico.
        List<CambioEstadoView> cambios = historial.ejecutar(id);
        assertEquals(2, cambios.size());
        assertEquals("ABIERTO", cambios.get(0).estadoAnterior());
        assertEquals("EN_REMEDIACION", cambios.get(0).estadoNuevo());
        assertEquals("CERRADO", cambios.get(1).estadoNuevo());

        // Dashboard: el hallazgo aparece en los conteos y en el promedio de cierre.
        DashboardAuditoriaView d = dashboard.ejecutar();
        long totalSeveridad = d.porSeveridad().stream().mapToLong(c -> c.total()).sum();
        assertTrue(totalSeveridad >= 1);
        assertTrue(d.promedioDiasCierrePorArea().stream()
            .anyMatch(p -> p.categoria().equals("Infraestructura") && p.promedioDias() > 0));
    }
}
