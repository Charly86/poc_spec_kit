<!--
Sync Impact Report
Version change: template -> 1.0.0
Modified principles:
- PRINCIPLE_1_NAME -> I. Simplicidad de POC
- PRINCIPLE_2_NAME -> II. Entrega incremental por historia
- PRINCIPLE_3_NAME -> III. Separacion backend/frontend
- PRINCIPLE_4_NAME -> IV. API REST verificable
- PRINCIPLE_5_NAME -> V. Pruebas duales API y web
Added sections:
- Restricciones tecnicas
- Flujo de desarrollo
Removed sections:
- Placeholder SECTION_2_NAME
- Placeholder SECTION_3_NAME
Templates requiring updates:
- updated: .specify/templates/plan-template.md
- updated: .specify/templates/spec-template.md
- updated: .specify/templates/tasks-template.md
- not present: .specify/templates/commands/*.md
- reviewed: AGENTS.md
Follow-up TODOs:
- None
-->
# Task CRUD POC Constitution

## Core Principles

### I. Simplicidad de POC
El proyecto MUST resolver solo el CRUD de una lista de tareas para una prueba de concepto.
Cada decision tecnica MUST favorecer codigo directo, dependencias minimas y comportamiento
demostrable sobre abstracciones preparadas para produccion. Toda dependencia o patron adicional
MUST justificarse en el plan con el problema concreto que resuelve.

Rationale: la POC debe validar el flujo funcional, no una arquitectura final.

### II. Entrega incremental por historia
La primera version MUST permitir crear tareas y listar tareas. Las capacidades de consultar
detalle, actualizar, completar y eliminar MUST incorporarse despues como historias separadas,
priorizadas y verificables de forma independiente. Ninguna historia posterior MAY bloquear
la demostracion de crear y listar tareas.

Rationale: cada incremento debe aportar valor visible y reducir el riesgo de entregar demasiado tarde.

### III. Separacion backend/frontend
El backend MUST vivir en una carpeta separada del frontend y exponer una API REST desacoplada.
El frontend MUST consumir la API por HTTP sin compartir modelos internos, codigo de persistencia
ni dependencias de build con el backend. La comunicacion entre ambos lados MUST estar descrita
mediante contratos HTTP claros.

Rationale: la separacion permite probar, ejecutar y cambiar cada lado sin mezclar responsabilidades.

### IV. API REST verificable
El backend MUST implementarse con Spring Boot, Java y Maven. La persistencia MUST ser local y
basica, adecuada para una POC, sin servicios externos obligatorios. La API MUST usar recursos
REST para tareas y devolver respuestas HTTP observables para exito, validacion y no encontrado.
El proyecto MUST NOT incluir usuarios, roles, sesiones ni autenticacion.

Rationale: una API pequena y comprobable es suficiente para demostrar el comportamiento CRUD.

### V. Pruebas duales API y web
Cada historia MUST poder validarse mediante peticiones HTTP a la API y mediante una interaccion
equivalente en la interfaz web. El plan y las tareas MUST indicar el metodo de prueba de API y
el metodo de prueba de UI para cada historia. Las pruebas automatizadas o manuales MAY ser
simples, pero MUST ser repetibles.

Rationale: la POC solo es completa si el contrato backend y la experiencia frontend demuestran
la misma capacidad.

## Restricciones tecnicas

- Backend: Spring Boot, Java, Maven, API REST y persistencia local basica.
- Frontend: aplicacion web desacoplada en carpeta separada, creada con Vite, HTML, CSS y
  JavaScript sin framework pesado.
- Estructura esperada: `backend/` para la API y `frontend/` para la web.
- Scope excluido: autenticacion, usuarios, roles, despliegue productivo, infraestructura cloud,
  observabilidad avanzada, colas, microservicios y frameworks frontend pesados.
- Las dependencias no incluidas en este documento MUST aparecer en el plan con una justificacion
  concreta y una alternativa mas simple considerada.

## Flujo de desarrollo

1. Especificar historias en orden incremental: crear/listar primero; detalle, actualizar,
   completar y eliminar despues.
2. Para cada historia, definir aceptacion observable por API HTTP y por UI web.
3. Planificar la estructura `backend/` y `frontend/` antes de crear tareas.
4. Implementar la historia P1 como MVP y validarla antes de iniciar historias posteriores.
5. Mantener cambios de backend y frontend separados salvo en contratos HTTP y documentacion.
6. Registrar cualquier desviacion de esta constitucion en la seccion de Complexity Tracking.

## Governance

Esta constitucion prevalece sobre cualquier practica o plantilla que contradiga sus reglas. Los planes,
especificaciones y tareas MUST verificar cumplimiento antes de iniciar implementacion.

Las enmiendas MUST incluir: motivo, impacto sobre historias existentes, impacto en plantillas
Spec Kit y decision de version. Los cambios se versionan con SemVer:

- MAJOR: elimina o redefine principios de forma incompatible.
- MINOR: agrega principios, secciones o restricciones materiales.
- PATCH: aclara texto sin cambiar reglas.

Cada plan MUST completar Constitution Check. Toda violacion MUST registrarse en Complexity
Tracking con una alternativa mas simple rechazada y su justificacion.

**Version**: 1.0.0 | **Ratified**: 2026-04-18 | **Last Amended**: 2026-04-18
