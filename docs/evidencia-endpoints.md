# Evidencia de endpoints (curl)

Pruebas contra `http://localhost:8080/api/hallazgos` (verificadas con
`mvn spring-boot:run`).

## Parte 1 — Clean Architecture

```bash
# Registrar un hallazgo -> 201 Created con el hallazgoId (UUID)
$ curl -X POST http://localhost:8080/api/hallazgos \
    -H "Content-Type: application/json" \
    -d '{"titulo":"Credenciales por defecto","descripcion":"Servidor QA",
         "areaResponsable":"Infraestructura","severidad":"ALTA","fechaDeteccion":"2026-08-01"}'
{"hallazgoId":"d90d4b2d-1d5e-41e0-aa71-bd2fbef603b6"}

# Iniciar remediacion sobre un hallazgo ABIERTO -> 200, estado EN_REMEDIACION
$ curl -X PATCH http://localhost:8080/api/hallazgos/{id}/iniciar-remediacion \
    -H "Content-Type: application/json" \
    -d '{"responsable":"Infra","fechaLimite":"2026-08-20","notas":"Rotar credenciales"}'
{"estado":"EN_REMEDIACION"}

# Cerrar un hallazgo ABIERTO sin plan -> 400 (transicion invalida / IllegalState)
$ curl -s -o /dev/null -w '%{http_code}' -X PATCH http://localhost:8080/api/hallazgos/{otroId}/cerrar
400

# Cerrar el hallazgo remediado -> 200, estado CERRADO
# Reabrir un hallazgo CERRADO -> 200, estado REABIERTO
# GET con id inexistente -> 404 Not Found
```

## Parte 2 — Dashboard e Historial

```bash
# Dashboard consolidado: conteo por severidad, por estado y promedio de dias de cierre por area
$ curl http://localhost:8080/api/hallazgos/dashboard
{"porSeveridad":[{"categoria":"ALTA","total":1},{"categoria":"CRITICA","total":1},
                 {"categoria":"MEDIA","total":1}],
 "porEstado":[{"categoria":"ABIERTO","total":1},{"categoria":"CERRADO","total":1},
              {"categoria":"REABIERTO","total":1}],
 "promedioDiasCierrePorArea":[{"categoria":"Desarrollo","promedioDias":58.0}]}

# Historial cronologico append-only de un hallazgo que recorrio todo el ciclo
$ curl http://localhost:8080/api/hallazgos/{id}/historial
[{"estadoAnterior":"ABIERTO","estadoNuevo":"EN_REMEDIACION","motivo":"Inicio de remediacion","fecha":"..."},
 {"estadoAnterior":"EN_REMEDIACION","estadoNuevo":"CERRADO","motivo":"Cierre de remediacion","fecha":"..."},
 {"estadoAnterior":"CERRADO","estadoNuevo":"REABIERTO","motivo":"El hallazgo reaparecio en auditoria externa","fecha":"..."}]
```

El promedio de días de cierre solo considera hallazgos en estado `CERRADO`: un
hallazgo reabierto deja de contar hasta que se vuelva a cerrar, como se observa
en el dashboard (solo aparece `Desarrollo`, no `Infraestructura`, cuyo hallazgo
fue reabierto).
