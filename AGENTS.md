# AGENTS.md

## Contexto Del Proyecto

Este repositorio contiene una prueba de concepto de lista de tareas basada en Spec Kit. El objetivo es demostrar un CRUD sencillo con separacion clara entre backend y frontend, evitando complejidad de produccion, autenticacion, usuarios o dependencias no justificadas.

La funcionalidad principal permite crear, listar, consultar detalle, actualizar, completar y eliminar tareas. Cada tarea tiene:

- `title`: obligatorio.
- `description`: opcional.
- `status`: `PENDING` o `COMPLETED`.
- `createdAt`: fecha de creacion asignada por el backend.

## Estructura

- `backend/`: API REST con Spring Boot.
- `frontend/`: aplicacion web desacoplada con Vite, HTML, CSS y JavaScript sin framework pesado.
- `specs/001-task-crud-poc/`: artefactos Spec Kit de la feature, incluyendo especificacion, plan, contrato OpenAPI, quickstart, tareas y plan de pruebas.

## Tecnologias

Backend:

- Java 21.
- Maven.
- Spring Boot.
- Spring Web.
- Spring Data JPA.
- Bean Validation.
- H2 local.

Frontend:

- Node.js 20+.
- npm.
- Vite.
- HTML, CSS y JavaScript nativo.

## Convenciones De Trabajo

- Mantener backend y frontend desacoplados en carpetas separadas.
- Exponer la API bajo `/api/tasks`.
- Usar el proxy de Vite para desarrollo local contra `http://localhost:8080`.
- Priorizar cambios simples, incrementales y testables.
- Cada historia debe poder validarse mediante API HTTP y mediante la interfaz web.
- No introducir usuarios, autenticacion, roles, servicios externos ni arquitectura innecesaria.
- Consultar `specs/001-task-crud-poc/plan.md` antes de cambios relevantes de arquitectura o estructura.
- Usar Context7 cuando se necesite buscar documentacion actualizada de librerias, frameworks o herramientas.

## Comandos Habituales

Backend:

```powershell
cd backend
mvn spring-boot:run
mvn test
```

Frontend:

```powershell
cd frontend
npm install
npm run dev
npm run build
```

Validacion completa:

- Ejecutar `mvn test` en `backend/`.
- Ejecutar `npm run build` en `frontend/`.
- Seguir `specs/001-task-crud-poc/quickstart.md` para validar API y UI.
