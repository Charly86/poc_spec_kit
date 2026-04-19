# Research: Task CRUD POC

## Decision: Spring Boot Maven backend with requested starters

Use a Maven Spring Boot application in `backend/` with Spring Web, Spring Data JPA,
Validation, H2, and Spring Boot test support.

**Rationale**: The requested stack matches the constitution and keeps the backend conventional.
Context7 Spring Boot documentation confirms the standard Maven parent/starter pattern and
the use of JPA repositories for simple CRUD persistence. Spring Web provides the REST layer,
Validation handles title validation, Spring Data JPA reduces CRUD boilerplate, and H2 gives
local persistence without an external service.

**Alternatives considered**:
- Plain in-memory collection: simpler, but conflicts with the requested Spring Data JPA and H2
  stack and gives weaker persistence behavior.
- File-based JSON persistence: local, but adds custom IO/error handling that JPA/H2 avoids.
- Full production database: unnecessary for the POC and violates the local-basic-persistence goal.

## Decision: REST API rooted at `/api/tasks`

Expose task operations under `/api/tasks`:

- `GET /api/tasks`
- `POST /api/tasks`
- `GET /api/tasks/{id}`
- `PUT /api/tasks/{id}`
- `PATCH /api/tasks/{id}/complete`
- `DELETE /api/tasks/{id}`

**Rationale**: Resource-oriented endpoints cover the CRUD lifecycle clearly and keep the
frontend/backend boundary testable through HTTP. A dedicated complete endpoint makes the
state transition explicit while allowing regular PUT updates for title and description.

**Alternatives considered**:
- Single generic PATCH endpoint: flexible, but less direct for a small POC.
- Separate `/complete` and `/reopen`: reopen is not required by the spec, so omit it.
- Nested user/task routes: users and authentication are explicitly out of scope.

## Decision: H2 local database via JPA entity

Persist `Task` as a JPA entity with generated numeric id, title, nullable description, enum
status, and created timestamp.

**Rationale**: H2 satisfies local persistence and integrates naturally with Spring Data JPA.
Generated IDs keep API usage simple. The status enum restricts values to `PENDING` and
`COMPLETED`. The creation timestamp is generated once and not changed on updates.

**Alternatives considered**:
- UUID ids: useful in distributed systems, but numeric ids are easier for a local POC.
- Store status as boolean: compact, but less expressive for API responses and future states.
- Client-provided creation date: rejected because the spec requires automatic creation date.

## Decision: Vite vanilla frontend with `/api` proxy

Use `frontend/` with Vite, plain HTML/CSS/JavaScript, and `vite.config.js` proxying `/api` to
`http://localhost:8080`.

**Rationale**: Context7 Vite documentation confirms `server.proxy` supports mapping a path
prefix such as `/api` to another local server. Keeping `/api` unchanged avoids frontend code
knowing the backend host in development and keeps the API paths identical between manual HTTP
checks and browser use.

**Alternatives considered**:
- Hardcode `http://localhost:8080` in frontend fetch calls: works locally, but creates coupling
  and bypasses the requested proxy preference.
- Add React/Vue/Svelte: unnecessary for a simple task-list POC and violates the no-heavy-framework
  constraint.
- Configure backend CORS only: useful if no proxy exists, but proxy keeps browser requests simple.

## Decision: Basic backend CRUD tests plus quickstart UI checks

Use Spring Boot tests for the REST API CRUD behavior and quickstart instructions for manual UI
validation. Frontend automated browser tests are deferred unless tasks later choose to add them.

**Rationale**: The constitution requires repeatable API and UI validation. Backend tests give
fast coverage for validation, not-found, create/list/detail/update/complete/delete behavior.
The quickstart gives a repeatable web validation path without adding a browser test dependency
before the POC needs it.

**Alternatives considered**:
- Full end-to-end browser automation now: valuable, but adds dependency and setup complexity.
- Manual-only API validation: too weak for the CRUD contract.
- Unit-only service tests: misses HTTP behavior, validation, and status responses.
