package com.example.auditoria.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Parte 2: proyecciones de agregacion con interface projections sobre el MISMO
// esquema; no se introduce otra base de datos ni un modelo de lectura separado.
public interface HallazgoJpaRepository extends JpaRepository<HallazgoJpaEntity, String> {

    @Query("SELECT h.severidad AS categoria, COUNT(h) AS total FROM HallazgoJpaEntity h GROUP BY h.severidad")
    List<ConteoProjection> contarPorSeveridad();

    @Query("SELECT h.estado AS categoria, COUNT(h) AS total FROM HallazgoJpaEntity h GROUP BY h.estado")
    List<ConteoProjection> contarPorEstado();

    // SQL nativo de H2: DATEDIFF('DAY', ...) no es portable en HQL, pero el calculo
    // del promedio de dias de cierre se resuelve en el motor de base de datos.
    @Query(value = "SELECT area_responsable AS categoria, " +
                   "AVG(DATEDIFF('DAY', fecha_deteccion, fecha_cierre)) AS promedio " +
                   "FROM hallazgos WHERE estado = 'CERRADO' GROUP BY area_responsable",
           nativeQuery = true)
    List<PromedioProjection> promedioDiasCierrePorArea();

    interface ConteoProjection {
        String getCategoria();
        Long getTotal();
    }

    interface PromedioProjection {
        String getCategoria();
        Double getPromedio();
    }
}
