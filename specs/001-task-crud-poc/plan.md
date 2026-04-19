# Implementation Plan: Task CRUD POC

**Branch**: `001-task-crud-poc` | **Date**: 2026-04-19 | **Spec**: `specs/001-task-crud-poc/spec.md`
**Input**: Feature specification from `/specs/001-task-crud-poc/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Build a simple task-list proof of concept with a REST backend and a decoupled web frontend.
The first increment demonstrates creating and listing tasks; later increments add detail,
update, complete, and delete. The backend exposes `/api/tasks` and stores tasks locally with
H2. The frontend is a Vite vanilla HTML/CSS/JavaScript app that calls the backend through a
local `/api` proxy during development.

## Technical Context

**Language/Version**: Java 21 for backend; browser JavaScript ES modules for frontend  
**Primary Dependencies**: Spring Boot, Spring Web, Spring Data JPA, Validation, H2, Maven; Vite for frontend tooling  
**Storage**: H2 local database via JPA; no external service  
**Testing**: Backend Maven tests with Spring Boot test support and MockMvc-style HTTP assertions; frontend basic manual/HTTP verification via quickstart  
**Target Platform**: Local developer machine, backend on `localhost:8080`, frontend dev server on Vite default port  
**Project Type**: Monorepo web app with separate backend and frontend folders  
**Performance Goals**: A user can complete create-and-list in under 30 seconds; full task flow demo in under 5 minutes  
**Constraints**: No users, authentication, roles, production infrastructure, or heavy frontend framework; API under `/api/tasks`; frontend uses `/api` proxy to backend  
**Scale/Scope**: Single-user local POC for CRUD task management

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **POC Simplicity**: PASS. Scope is limited to task CRUD with local H2 persistence.
  Spring Data JPA and H2 are justified by the user-requested local persistence stack.
- **Incremental Delivery**: PASS. P1 is create/list; detail, update/complete, and delete
  remain separate later stories.
- **Backend/Frontend Separation**: PASS. Source layout is `backend/` plus `frontend/`;
  integration is only through documented HTTP contracts.
- **REST Backend Stack**: PASS. Backend uses Spring Boot, Java, Maven, REST resources,
  H2 local persistence, and no authentication.
- **Light Frontend Stack**: PASS. Frontend uses Vite with HTML, CSS, and JavaScript
  without a framework.
- **Dual Verification**: PASS. Each story has API and web UI verification in the spec;
  quickstart covers both paths.

Post-design re-check: PASS. Research, data model, OpenAPI contract, and quickstart preserve
all constitution gates.

## Project Structure

### Documentation (this feature)

```text
specs/001-task-crud-poc/
+-- plan.md
+-- research.md
+-- data-model.md
+-- quickstart.md
+-- contracts/
|   +-- openapi.yaml
+-- checklists/
    +-- requirements.md
```

### Source Code (repository root)

```text
backend/
+-- pom.xml
+-- src/main/java/com/example/taskcrud/
|   +-- TaskCrudApplication.java
|   +-- task/
|       +-- Task.java
|       +-- TaskStatus.java
|       +-- TaskRepository.java
|       +-- TaskService.java
|       +-- TaskController.java
|       +-- TaskRequest.java
|       +-- TaskResponse.java
|       +-- TaskNotFoundException.java
|       +-- ApiExceptionHandler.java
+-- src/main/resources/
|   +-- application.properties
+-- src/test/java/com/example/taskcrud/
    +-- task/
        +-- TaskControllerTest.java

frontend/
+-- package.json
+-- vite.config.js
+-- index.html
+-- src/
    +-- main.js
    +-- api.js
    +-- styles.css
```

**Structure Decision**: Use the required monorepo split with exactly two implementation
folders: `backend/` for the Spring Boot API and `frontend/` for the Vite vanilla web app.
Feature design artifacts remain under `specs/001-task-crud-poc/`.

## Complexity Tracking

No constitution violations. No extra architecture or production-only infrastructure is planned.
