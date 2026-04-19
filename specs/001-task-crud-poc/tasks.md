---
description: "Task list for Task CRUD POC implementation"
---

# Tasks: Task CRUD POC

**Input**: Design documents from `/specs/001-task-crud-poc/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/openapi.yaml, quickstart.md

**Verification**: Each user story includes API HTTP verification and web UI verification.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (US1, US2, US3, US4)
- Include exact file paths in descriptions

## Path Conventions

- **Backend**: `backend/src/main/java/com/example/taskcrud/`, `backend/src/test/java/com/example/taskcrud/`
- **Frontend**: `frontend/index.html`, `frontend/src/`, `frontend/vite.config.js`
- **Feature docs**: `specs/001-task-crud-poc/`
- **QA docs**: `specs/001-task-crud-poc/test-plan.md`

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Create the monorepo skeleton and baseline backend/frontend projects.

- [X] T001 Create Spring Boot Maven project descriptor with Web, Data JPA, Validation, H2, and test dependencies in backend/pom.xml
- [X] T002 Create backend application entry point in backend/src/main/java/com/example/taskcrud/TaskCrudApplication.java
- [X] T003 Create backend resource and test package directories with placeholder .gitkeep files in backend/src/main/resources/.gitkeep and backend/src/test/java/com/example/taskcrud/.gitkeep
- [X] T004 Create Vite package scripts and dev dependencies in frontend/package.json
- [X] T005 Create frontend HTML shell in frontend/index.html
- [X] T006 Create frontend source placeholders in frontend/src/main.js, frontend/src/api.js, and frontend/src/styles.css

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish shared persistence, validation, error handling, and local development wiring before story work.

**CRITICAL**: No user story work can begin until this phase is complete.

- [X] T007 Configure H2 datasource, JPA schema generation, and application name in backend/src/main/resources/application.properties
- [X] T008 Configure Vite dev proxy from /api to http://localhost:8080 in frontend/vite.config.js
- [X] T009 Create task package directory with placeholder .gitkeep in backend/src/main/java/com/example/taskcrud/task/.gitkeep
- [X] T010 [P] Create TaskStatus enum with PENDING and COMPLETED values in backend/src/main/java/com/example/taskcrud/task/TaskStatus.java
- [X] T011 [P] Create Task JPA entity with id, title, description, status, and createdAt fields in backend/src/main/java/com/example/taskcrud/task/Task.java
- [X] T012 [P] Create TaskRepository extending JpaRepository in backend/src/main/java/com/example/taskcrud/task/TaskRepository.java
- [X] T013 [P] Create TaskRequest validation DTO with non-blank title and optional description in backend/src/main/java/com/example/taskcrud/task/TaskRequest.java
- [X] T014 [P] Create TaskResponse DTO mapping id, title, description, status, and createdAt in backend/src/main/java/com/example/taskcrud/task/TaskResponse.java
- [X] T015 [P] Create TaskNotFoundException in backend/src/main/java/com/example/taskcrud/task/TaskNotFoundException.java
- [X] T016 Create ApiExceptionHandler for validation and not-found JSON responses in backend/src/main/java/com/example/taskcrud/task/ApiExceptionHandler.java
- [X] T017 Create TaskService class with repository constructor and response mapping helper in backend/src/main/java/com/example/taskcrud/task/TaskService.java
- [X] T018 Create TaskController class mapped to /api/tasks with constructor injection in backend/src/main/java/com/example/taskcrud/task/TaskController.java
- [X] T019 Create consolidated QA test plan with scope, test types, environments, entry/exit criteria, risks, and story coverage in specs/001-task-crud-poc/test-plan.md
- [X] T020 Verify test-plan traceability against specs/001-task-crud-poc/spec.md, specs/001-task-crud-poc/contracts/openapi.yaml, specs/001-task-crud-poc/quickstart.md, and all US1-US4 verification tasks in specs/001-task-crud-poc/tasks.md

**Checkpoint**: Foundation ready - user story implementation can now begin.

---

## Phase 3: User Story 1 - Crear y listar tareas (Priority: P1) MVP

**Goal**: Users can create a task with a required title and see it in the task list as pending with creation date.

**Independent Test**: Create a task from an empty list and confirm it appears in list results with id, title, status PENDING, and createdAt.

### Verification for User Story 1

- [X] T021 [P] [US1] Add API tests for POST /api/tasks success, POST /api/tasks blank-title validation, and GET /api/tasks list in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java
- [X] T022 [P] [US1] Add create/list web verification notes to specs/001-task-crud-poc/quickstart.md

### Implementation for User Story 1

- [X] T023 [US1] Implement createTask and listTasks service methods in backend/src/main/java/com/example/taskcrud/task/TaskService.java
- [X] T024 [US1] Implement POST /api/tasks and GET /api/tasks endpoints in backend/src/main/java/com/example/taskcrud/task/TaskController.java
- [X] T025 [US1] Implement createTask and listTasks fetch helpers using relative /api/tasks URLs in frontend/src/api.js
- [X] T026 [US1] Build create-task form, empty-state message, task list rendering, and submit handling in frontend/src/main.js
- [X] T027 [US1] Style create/list layout, validation messages, pending status, and responsive spacing in frontend/src/styles.css

**Checkpoint**: User Story 1 is independently functional and demonstrates the MVP.

---

## Phase 4: User Story 2 - Consultar detalle de una tarea (Priority: P2)

**Goal**: Users can select a task and see title, description, status, and creation date details.

**Independent Test**: Create a task with description, open its detail, and verify all task fields are shown; requesting an unknown task reports not found.

### Verification for User Story 2

- [X] T028 [P] [US2] Add API tests for GET /api/tasks/{id} success and not-found behavior in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java
- [X] T029 [P] [US2] Add detail web verification notes to specs/001-task-crud-poc/quickstart.md

### Implementation for User Story 2

- [X] T030 [US2] Implement getTask service method in backend/src/main/java/com/example/taskcrud/task/TaskService.java
- [X] T031 [US2] Implement GET /api/tasks/{id} endpoint in backend/src/main/java/com/example/taskcrud/task/TaskController.java
- [X] T032 [US2] Implement getTask fetch helper in frontend/src/api.js
- [X] T033 [US2] Add task selection and detail panel rendering in frontend/src/main.js
- [X] T034 [US2] Style selected task and detail panel states in frontend/src/styles.css

**Checkpoint**: User Stories 1 and 2 work independently through API and UI.

---

## Phase 5: User Story 3 - Actualizar y completar tareas (Priority: P3)

**Goal**: Users can edit title/description and mark a pending task as completed.

**Independent Test**: Create a pending task, update title or description, complete it, and verify list and detail reflect the changes while createdAt remains unchanged.

### Verification for User Story 3

- [X] T035 [P] [US3] Add API tests for PUT /api/tasks/{id} success, blank-title validation, not-found behavior, and createdAt preservation in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java
- [X] T036 [P] [US3] Add API tests for PATCH /api/tasks/{id}/complete success, idempotent completed state, and not-found behavior in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java
- [X] T037 [P] [US3] Add update/complete web verification notes to specs/001-task-crud-poc/quickstart.md

### Implementation for User Story 3

- [X] T038 [US3] Implement updateTask and completeTask service methods in backend/src/main/java/com/example/taskcrud/task/TaskService.java
- [X] T039 [US3] Implement PUT /api/tasks/{id} and PATCH /api/tasks/{id}/complete endpoints in backend/src/main/java/com/example/taskcrud/task/TaskController.java
- [X] T040 [US3] Implement updateTask and completeTask fetch helpers in frontend/src/api.js
- [X] T041 [US3] Add edit form state, save handling, and complete button handling in frontend/src/main.js
- [X] T042 [US3] Style edit controls, completed status, and disabled/feedback states in frontend/src/styles.css

**Checkpoint**: User Stories 1, 2, and 3 work independently through API and UI.

---

## Phase 6: User Story 4 - Eliminar tareas (Priority: P4)

**Goal**: Users can delete a task and confirm it no longer appears or resolves by detail.

**Independent Test**: Create a task, delete it, confirm it disappears from the list, and confirm detail lookup reports not found.

### Verification for User Story 4

- [X] T043 [P] [US4] Add API tests for DELETE /api/tasks/{id} success and not-found behavior in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java
- [X] T044 [P] [US4] Add delete web verification notes to specs/001-task-crud-poc/quickstart.md

### Implementation for User Story 4

- [X] T045 [US4] Implement deleteTask service method in backend/src/main/java/com/example/taskcrud/task/TaskService.java
- [X] T046 [US4] Implement DELETE /api/tasks/{id} endpoint in backend/src/main/java/com/example/taskcrud/task/TaskController.java
- [X] T047 [US4] Implement deleteTask fetch helper in frontend/src/api.js
- [X] T048 [US4] Add delete action handling and selected-detail cleanup in frontend/src/main.js
- [X] T049 [US4] Style destructive action affordance and post-delete feedback in frontend/src/styles.css

**Checkpoint**: Full CRUD is independently functional through API and UI.

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: Validate the full POC, remove unnecessary complexity, and align docs with delivered behavior.

- [X] T050 Run backend test suite with mvn test from backend/pom.xml
- [X] T051 Run frontend install and dev-server smoke check from frontend/package.json
- [X] T052 Execute API quickstart commands and record any corrections in specs/001-task-crud-poc/quickstart.md
- [X] T053 Execute Web UI Verification steps and record any corrections in specs/001-task-crud-poc/quickstart.md
- [X] T054 Review code for no authentication, no users, no external service dependencies, and update specs/001-task-crud-poc/plan.md if deviations exist
- [X] T055 Remove placeholder .gitkeep files after real files exist in backend/src/main/resources/.gitkeep, backend/src/test/java/com/example/taskcrud/.gitkeep, and backend/src/main/java/com/example/taskcrud/task/.gitkeep

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup completion; blocks all user stories.
- **User Story 1 (Phase 3)**: Depends on Foundational; delivers MVP create/list.
- **User Story 2 (Phase 4)**: Depends on US1 for existing task data and list selection.
- **User Story 3 (Phase 5)**: Depends on US1 and US2 for editable task data and detail refresh.
- **User Story 4 (Phase 6)**: Depends on US1 and US2 for delete target selection and post-delete detail behavior.
- **Polish (Phase 7)**: Depends on all desired user stories.

### User Story Dependencies

- **US1 Crear y listar tareas**: Required MVP and base for all later stories.
- **US2 Consultar detalle de una tarea**: Uses task ids created/listed in US1.
- **US3 Actualizar y completar tareas**: Uses task detail from US2 and list refresh from US1.
- **US4 Eliminar tareas**: Uses task selection/detail from US2 and list refresh from US1.

### Within Each User Story

- Verification tasks are defined before implementation tasks.
- The consolidated test plan T019 and its traceability verification T020 must be complete before story-specific verification tasks are executed.
- Backend service methods before controller endpoints.
- Controller endpoints before frontend API helpers.
- Frontend API helpers before UI event handling.
- UI behavior before styling polish.
- Complete each checkpoint before moving to the next story.

### Parallel Opportunities

- Setup tasks T002-T006 can run in parallel after T001 if directories are created as needed.
- Foundational tasks T010-T015 can run in parallel after T009.
- Test plan task T019 can run in parallel with foundational backend skeleton tasks T010-T015, then T020 validates traceability after T019 is complete.
- Verification documentation tasks can run in parallel with backend tests within the same story.
- Frontend styling tasks can run in parallel with backend endpoint work after UI structure is known.

---

## Parallel Example: User Story 1

```text
Task: "T021 [P] [US1] Add API tests for POST /api/tasks success, POST /api/tasks blank-title validation, and GET /api/tasks list in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java"
Task: "T022 [P] [US1] Add create/list web verification notes to specs/001-task-crud-poc/quickstart.md"
```

---

## Parallel Example: User Story 2

```text
Task: "T028 [P] [US2] Add API tests for GET /api/tasks/{id} success and not-found behavior in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java"
Task: "T029 [P] [US2] Add detail web verification notes to specs/001-task-crud-poc/quickstart.md"
```

---

## Parallel Example: User Story 3

```text
Task: "T035 [P] [US3] Add API tests for PUT /api/tasks/{id} success, blank-title validation, not-found behavior, and createdAt preservation in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java"
Task: "T036 [P] [US3] Add API tests for PATCH /api/tasks/{id}/complete success, idempotent completed state, and not-found behavior in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java"
Task: "T037 [P] [US3] Add update/complete web verification notes to specs/001-task-crud-poc/quickstart.md"
```

---

## Parallel Example: User Story 4

```text
Task: "T043 [P] [US4] Add API tests for DELETE /api/tasks/{id} success and not-found behavior in backend/src/test/java/com/example/taskcrud/task/TaskControllerTest.java"
Task: "T044 [P] [US4] Add delete web verification notes to specs/001-task-crud-poc/quickstart.md"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup.
2. Complete Phase 2: Foundational.
3. Complete Phase 3: User Story 1.
4. Stop and validate create/list through backend tests, API quickstart commands, and the web UI.

### Incremental Delivery

1. Deliver US1 create/list as the MVP.
2. Add US2 detail without changing US1 behavior.
3. Add US3 update/complete while preserving createdAt and validation.
4. Add US4 delete and verify deleted tasks are removed from list and detail.
5. Run Polish tasks after all desired stories are complete.

### Validation Gates

- Each story must pass its API tests before its UI verification is considered complete.
- Each story must be demonstrable through both HTTP and the web UI before moving to the next phase.
- No task may introduce users, authentication, roles, or external service dependencies.
