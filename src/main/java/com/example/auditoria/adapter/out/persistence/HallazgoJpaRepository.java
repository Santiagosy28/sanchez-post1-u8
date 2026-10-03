package com.example.auditoria.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// En la Parte 2 se extiende con proyecciones de agregacion (interface projections).
public interface HallazgoJpaRepository extends JpaRepository<HallazgoJpaEntity, String> {
}
