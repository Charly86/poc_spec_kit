---

description: "Task list template for feature implementation"
---

# Tasks: [FEATURE NAME]

**Input**: Design documents from `/specs/[###-feature-name]/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Verification**: Each user story MUST include API HTTP verification and web UI
verification tasks. Automated tests are preferred when practical; manual checks
are acceptable for the POC when they are repeatable and documented.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Path Conventions

- **Single project**: `src/`, `tests/` at repository root
- **Web app**: `backend/src/main/java/`, `backend/src/test/java/`, `frontend/src/`
- **Mobile**: `api/src/`, `ios/src/` or `android/src/`
- Paths shown below assume single project - adjust based on plan.md structure

<!-- 
  ============================================================================
  IMPORTANT: The tasks below are SAMPLE TASKS for illustration purposes only.
  
  The /speckit.tasks command MUST replace these with actual tasks based on:
  - User stories from spec.md (with their priorities P1, P2, P3...)
  - Feature requirements from plan.md
  - Entities from data-model.md
  - Endpoints from contracts/
  
  Tasks MUST be organized by user story so each story can be:
  - Implemented independently
  - Tested independently
  - Delivered as an MVP increment
  
  DO NOT keep these sample tasks in the generated tasks.md file.
  ============================================================================
-->

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and basic structure

- [ ] T001 Create project structure per implementation plan
- [ ] T002 Initialize Spring Boot Maven backend and Vite frontend dependencies
- [ ] T003 [P] Configure linting and formatting tools

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Core infrastructure that MUST be complete before ANY user story can be implemented

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

Examples of foundational tasks (adjust based on your project):

- [ ] T004 Setup basic local persistence for tasks
- [ ] T005 [P] Setup Spring Boot REST routing structure
- [ ] T006 [P] Setup Vite frontend structure and API base configuration
- [ ] T007 Create base Task model/entity used by all stories
- [ ] T008 Configure simple validation and error responses

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - [Title] (Priority: P1) 🎯 MVP

**Goal**: [Brief description of what this story delivers]

**Independent Test**: [How to verify this story works on its own]

### Verification for User Story 1

> **NOTE: Include both API HTTP verification and web UI verification.**

- [ ] T010 [P] [US1] API verification for [endpoint] in backend/src/test/java/[package]/[TestName].java or quickstart.md
- [ ] T011 [P] [US1] Web UI verification for [user journey] in frontend/[test-or-quickstart-location]

### Implementation for User Story 1

- [ ] T012 [P] [US1] Create [Entity1] model in backend/src/main/java/[package]/[Entity1].java
- [ ] T013 [P] [US1] Create [Entity2] model in backend/src/main/java/[package]/[Entity2].java
- [ ] T014 [US1] Implement [Service] in backend/src/main/java/[package]/[Service].java (depends on T012, T013)
- [ ] T015 [US1] Implement [endpoint/feature] in backend/src/main/java/[package]/[Controller].java
- [ ] T016 [US1] Add validation and error handling
- [ ] T017 [US1] Wire frontend interaction to the REST API

**Checkpoint**: At this point, User Story 1 should be fully functional and testable independently

---

## Phase 4: User Story 2 - [Title] (Priority: P2)

**Goal**: [Brief description of what this story delivers]

**Independent Test**: [How to verify this story works on its own]

### Verification for User Story 2

- [ ] T018 [P] [US2] API verification for [endpoint] in backend/src/test/java/[package]/[TestName].java or quickstart.md
- [ ] T019 [P] [US2] Web UI verification for [user journey] in frontend/[test-or-quickstart-location]

### Implementation for User Story 2

- [ ] T020 [P] [US2] Create [Entity] model in backend/src/main/java/[package]/[Entity].java
- [ ] T021 [US2] Implement [Service] in backend/src/main/java/[package]/[Service].java
- [ ] T022 [US2] Implement [endpoint/feature] in backend/src/main/java/[package]/[Controller].java
- [ ] T023 [US2] Integrate with User Story 1 components (if needed)

**Checkpoint**: At this point, User Stories 1 AND 2 should both work independently

---

## Phase 5: User Story 3 - [Title] (Priority: P3)

**Goal**: [Brief description of what this story delivers]

**Independent Test**: [How to verify this story works on its own]

### Verification for User Story 3

- [ ] T024 [P] [US3] API verification for [endpoint] in backend/src/test/java/[package]/[TestName].java or quickstart.md
- [ ] T025 [P] [US3] Web UI verification for [user journey] in frontend/[test-or-quickstart-location]

### Implementation for User Story 3

- [ ] T026 [P] [US3] Create [Entity] model in backend/src/main/java/[package]/[Entity].java
- [ ] T027 [US3] Implement [Service] in backend/src/main/java/[package]/[Service].java
- [ ] T028 [US3] Implement [endpoint/feature] in backend/src/main/java/[package]/[Controller].java

**Checkpoint**: All user stories should now be independently functional

---

[Add more user story phases as needed, following the same pattern]

---

## Phase N: Polish & Cross-Cutting Concerns

**Purpose**: Improvements that affect multiple user stories

- [ ] TXXX [P] Documentation updates in docs/
- [ ] TXXX Code cleanup and refactoring
- [ ] TXXX Performance optimization across all stories
- [ ] TXXX [P] Additional unit tests (if requested) in tests/unit/
- [ ] TXXX Remove unjustified dependencies or production-only complexity
- [ ] TXXX Run quickstart.md validation

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: All depend on Foundational phase completion
  - User stories can then proceed in parallel (if staffed)
  - Or sequentially in priority order (P1 → P2 → P3)
- **Polish (Final Phase)**: Depends on all desired user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Can start after Foundational (Phase 2) - No dependencies on other stories
- **User Story 2 (P2)**: Can start after Foundational (Phase 2) - May integrate with US1 but should be independently testable
- **User Story 3 (P3)**: Can start after Foundational (Phase 2) - May integrate with US1/US2 but should be independently testable

### Within Each User Story

- API and web UI verification tasks MUST be defined before implementation
- Models before services
- Services before endpoints
- Core implementation before integration
- Story complete before moving to next priority

### Parallel Opportunities

- All Setup tasks marked [P] can run in parallel
- All Foundational tasks marked [P] can run in parallel (within Phase 2)
- Once Foundational phase completes, all user stories can start in parallel (if team capacity allows)
- All verification tasks for a user story marked [P] can run in parallel
- Models within a story marked [P] can run in parallel
- Different user stories can be worked on in parallel by different team members

---

## Parallel Example: User Story 1

```bash
# Launch all verification tasks for User Story 1 together:
Task: "API verification for [endpoint] in backend/src/test/java/[package]/[TestName].java or quickstart.md"
Task: "Web UI verification for [user journey] in frontend/[test-or-quickstart-location]"

# Launch all models for User Story 1 together:
Task: "Create [Entity1] model in backend/src/main/java/[package]/[Entity1].java"
Task: "Create [Entity2] model in backend/src/main/java/[package]/[Entity2].java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational (CRITICAL - blocks all stories)
3. Complete Phase 3: User Story 1
4. **STOP and VALIDATE**: Test User Story 1 independently
5. Deploy/demo if ready

### Incremental Delivery

1. Complete Setup + Foundational → Foundation ready
2. Add User Story 1 → Test independently → Deploy/Demo (MVP!)
3. Add User Story 2 → Test independently → Deploy/Demo
4. Add User Story 3 → Test independently → Deploy/Demo
5. Each story adds value without breaking previous stories

### Parallel Team Strategy

With multiple developers:

1. Team completes Setup + Foundational together
2. Once Foundational is done:
   - Developer A: User Story 1
   - Developer B: User Story 2
   - Developer C: User Story 3
3. Stories complete and integrate independently

---

## Notes

- [P] tasks = different files, no dependencies
- [Story] label maps task to specific user story for traceability
- Each user story should be independently completable and testable
- Verify tests fail before implementing
- Commit after each task or logical group
- Stop at any checkpoint to validate story independently
- Avoid: vague tasks, same file conflicts, cross-story dependencies that break independence
