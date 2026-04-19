# Test Plan: Task CRUD POC

## Executive Summary

This plan verifies the Task CRUD POC across the backend HTTP API and the decoupled web UI.
The goal is to prove that a local user can create, list, inspect, update, complete, and delete
tasks without users, authentication, or external services.

## Test Scope

**In Scope:**

- Task creation with required title and optional description
- Listing tasks
- Viewing task detail
- Updating title and description
- Completing a task
- Deleting a task
- Blank-title validation
- Not-found handling for detail, update, complete, and delete
- Frontend integration through the Vite `/api` proxy

**Out of Scope:**

- User accounts, ownership, roles, and authentication
- Production deployment, cloud infrastructure, and external databases
- Mobile app packaging
- Reopening a completed task

## Test Strategy

| Type | Coverage | Artifact |
|------|----------|----------|
| API integration | REST endpoints, validation, not-found handling, persistence behavior | `backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java` |
| Manual UI smoke | Create/list/detail/update/complete/delete through browser | `specs/001-task-crud-poc/quickstart.md` |
| Contract review | Endpoint shape and response semantics | `specs/001-task-crud-poc/contracts/openapi.yaml` |
| Regression | Full CRUD path after each story | `specs/001-task-crud-poc/tasks.md` checkpoints |

## Test Environment

- OS: Local developer machine
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:5173`
- Storage: Local H2 database
- Browser: Current Chromium, Edge, Firefox, or equivalent desktop browser

## Entry Criteria

- [ ] `backend/` and `frontend/` project skeletons exist
- [ ] Backend starts on `localhost:8080`
- [ ] Frontend starts on `localhost:5173`
- [ ] Vite proxy forwards `/api` to the backend
- [ ] API contract is available at `specs/001-task-crud-poc/contracts/openapi.yaml`

## Exit Criteria

- [ ] Backend test suite passes with `mvn test`
- [ ] Quickstart API verification commands complete with expected status codes
- [ ] Web UI verification covers create, list, detail, update, complete, delete, and validation
- [ ] No critical or high-severity bugs remain open
- [ ] No users, authentication, or external service dependencies were introduced

## Story Coverage Matrix

| Story | API Verification | Web Verification | Required Outcome |
|-------|------------------|------------------|------------------|
| US1 Crear y listar | POST `/api/tasks`, GET `/api/tasks`, blank-title 400 | Create form and visible task list | New task appears as pending with created date |
| US2 Consultar detalle | GET `/api/tasks/{id}`, 404 for missing id | Select task and view detail panel | Detail shows title, description, status, created date |
| US3 Actualizar y completar | PUT `/api/tasks/{id}`, PATCH `/api/tasks/{id}/complete` | Edit form and complete action | Updated fields persist; status becomes completed |
| US4 Eliminar | DELETE `/api/tasks/{id}`, 404 for missing id | Delete action from selected task | Deleted task disappears from list and detail |

## Risks And Mitigations

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| API and UI behavior diverge | Medium | High | Keep quickstart UI checks mapped to API tests |
| H2 data persists between manual runs unexpectedly | Medium | Medium | Document cleanup expectations and isolate automated tests |
| Blank-title validation differs between frontend and backend | Medium | Medium | Assert backend validation and surface backend errors in UI |
| Proxy misconfiguration blocks local UI | Low | High | Verify `/api` proxy before UI story checks |

## Traceability Verification

- `spec.md` defines each user story and its API/UI test path.
- `contracts/openapi.yaml` defines every HTTP endpoint required by the stories.
- `quickstart.md` gives repeatable API commands and web UI checks.
- `tasks.md` includes verification tasks before implementation tasks for US1-US4.
