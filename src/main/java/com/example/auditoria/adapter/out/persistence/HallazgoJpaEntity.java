package com.example.auditoria.adapter.out.persistence;

import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.Severidad;
import jakarta.persistence.*;

import java.time.LocalDate;

// Entidad JPA — pertenece al circulo Frameworks & Drivers. Solo aqui se conoce JPA.
@Entity
@Table(name = "hallazgos")
public class HallazgoJpaEntity {

    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String areaResponsable;
    @Enumerated(EnumType.STRING)
    private Severidad severidad;
    @Enumerated(EnumType.STRING)
    private EstadoHallazgo estado;
    private LocalDate fechaDeteccion;
    private LocalDate fechaCierre;
    private String planResponsable;
    private LocalDate planFechaLimite;
    private String planNotas;

    public HallazgoJpaEntity() {
    }

    public String getId()                    { return id; }
    public void setId(String id)             { this.id = id; }
    public String getTitulo()                { return titulo; }
    public void setTitulo(String titulo)     { this.titulo = titulo; }
    public String getDescripcion()           { return descripcion; }
    public void setDescripcion(String d)     { this.descripcion = d; }
    public String getAreaResponsable()       { return areaResponsable; }
    public void setAreaResponsable(String a) { this.areaResponsable = a; }
    public Severidad getSeveridad()          { return severidad; }
    public void setSeveridad(Severidad s)    { this.severidad = s; }
    public EstadoHallazgo getEstado()        { return estado; }
    public void setEstado(EstadoHallazgo e)  { this.estado = e; }
    public LocalDate getFechaDeteccion()     { return fechaDeteccion; }
    public void setFechaDeteccion(LocalDate f) { this.fechaDeteccion = f; }
    public LocalDate getFechaCierre()        { return fechaCierre; }
    public void setFechaCierre(LocalDate f)  { this.fechaCierre = f; }
    public String getPlanResponsable()       { return planResponsable; }
    public void setPlanResponsable(String p) { this.planResponsable = p; }
    public LocalDate getPlanFechaLimite()    { return planFechaLimite; }
    public void setPlanFechaLimite(LocalDate f) { this.planFechaLimite = f; }
    public String getPlanNotas()             { return planNotas; }
    public void setPlanNotas(String n)       { this.planNotas = n; }
}
