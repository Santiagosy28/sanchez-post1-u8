package com.example.auditoria.usecase.port;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.HallazgoId;

import java.util.List;
import java.util.Optional;
// ConteoCategoria y PromedioCategoria estan en este mismo paquete (usecase.port).

// Puerto de salida del circulo Use Cases. En la Parte 2 se extiende con
// metodos de consulta agregada (mismo puerto, sin stack de lectura separado).
public interface HallazgoRepositoryPort {
    void guardar(HallazgoAuditoria hallazgo);
    Optional<HallazgoAuditoria> buscarPorId(HallazgoId id);
    List<HallazgoAuditoria> buscarTodos();

    // Metodos anadidos en la Parte 2 — mismo puerto, sin stack de lectura separado.
    List<ConteoCategoria> contarPorSeveridad();
    List<ConteoCategoria> contarPorEstado();
    List<PromedioCategoria> promedioDiasCierrePorArea();
}
