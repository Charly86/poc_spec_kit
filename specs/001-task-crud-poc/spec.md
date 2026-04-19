# Feature Specification: Task CRUD POC

**Feature Branch**: `001-task-crud-poc`  
**Created**: 2026-04-19  
**Status**: Draft  
**Input**: User description: "Crea una prueba de concepto de una lista de tareas donde un usuario pueda crear, consultar, actualizar, completar y eliminar tareas desde una interfaz web simple desacoplada del backend. Cada tarea tendrá título obligatorio, descripción opcional, estado pendiente o completada, y fecha de creación. No habrá usuarios ni autenticación. La funcionalidad también debe poder validarse mediante una API HTTP sencilla. El frontend y el backend deben vivir en carpetas separadas."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Crear y listar tareas (Priority: P1)

Como persona que gestiona una lista simple, quiero crear una tarea con la información mínima
necesaria y verla inmediatamente en la lista para confirmar que quedó registrada.

**Why this priority**: Es el MVP de la prueba de concepto: sin crear y listar tareas no existe
flujo útil que demostrar.

**Independent Test**: Con una lista inicialmente vacía, crear una tarea con título válido y
comprobar que aparece en el listado con estado pendiente y fecha de creación.

**API Test**: Enviar una petición para crear una tarea con título y, después, una petición para
listar tareas; la respuesta debe incluir la nueva tarea con identificador, título, estado
pendiente y fecha de creación.

**Web UI Test**: Abrir la interfaz web, introducir un título en el formulario de creación,
confirmar la acción y comprobar que la nueva tarea aparece en la lista visible.

**Acceptance Scenarios**:

1. **Given** que no hay tareas, **When** el usuario crea una tarea con título "Comprar leche",
   **Then** la lista muestra una tarea pendiente con ese título.
2. **Given** que existen varias tareas, **When** el usuario abre la lista, **Then** ve todas las
   tareas registradas con título, estado y fecha de creación.
3. **Given** que el usuario intenta crear una tarea sin título, **When** confirma la acción,
   **Then** el sistema rechaza la creación y muestra un mensaje de validación claro.

---

### User Story 2 - Consultar detalle de una tarea (Priority: P2)

Como persona que revisa sus tareas, quiero abrir una tarea concreta para ver todos sus datos y
confirmar su descripción, estado y fecha de creación.

**Why this priority**: La consulta de detalle permite validar que cada tarea se trata como un
elemento individual y prepara los flujos de edición posteriores.

**Independent Test**: Crear una tarea con título y descripción, abrir su detalle y comprobar que
se muestran exactamente sus datos.

**API Test**: Crear o seleccionar una tarea existente y solicitar su detalle por identificador; la
respuesta debe devolver la tarea correspondiente con todos sus campos.

**Web UI Test**: Desde el listado, seleccionar una tarea y comprobar que la pantalla o panel de
detalle muestra título, descripción si existe, estado y fecha de creación.

**Acceptance Scenarios**:

1. **Given** que existe una tarea con descripción, **When** el usuario consulta su detalle,
   **Then** ve título, descripción, estado y fecha de creación.
2. **Given** que se solicita una tarea inexistente, **When** el usuario o cliente consulta su
   detalle, **Then** el sistema informa que la tarea no existe sin mostrar datos incorrectos.

---

### User Story 3 - Actualizar y completar tareas (Priority: P3)

Como persona que mantiene su lista al día, quiero modificar el contenido de una tarea y marcarla
como completada para reflejar su estado real.

**Why this priority**: Actualizar y completar tareas convierten la lista en una herramienta útil
después de demostrar el alta, listado y detalle.

**Independent Test**: Crear una tarea pendiente, cambiar su título o descripción, marcarla como
completada y comprobar que los cambios quedan reflejados en lista y detalle.

**API Test**: Enviar una petición de actualización para título o descripción y otra para cambiar
el estado a completada; las respuestas y consultas posteriores deben reflejar los cambios.

**Web UI Test**: Editar una tarea desde la interfaz, guardar los cambios, marcarla como
completada y verificar que el listado y el detalle muestran el nuevo contenido y estado.

**Acceptance Scenarios**:

1. **Given** que existe una tarea pendiente, **When** el usuario cambia su título y guarda,
   **Then** el nuevo título aparece en la lista y en el detalle.
2. **Given** que existe una tarea pendiente, **When** el usuario la marca como completada,
   **Then** la tarea queda identificada como completada.
3. **Given** que el usuario intenta actualizar una tarea dejando el título vacío, **When** guarda,
   **Then** el sistema rechaza el cambio y conserva el valor anterior.

---

### User Story 4 - Eliminar tareas (Priority: P4)

Como persona que limpia su lista, quiero eliminar una tarea que ya no necesito para que deje de
aparecer en la lista.

**Why this priority**: La eliminación completa el ciclo CRUD, pero puede entregarse después de
las capacidades necesarias para crear, consultar y modificar tareas.

**Independent Test**: Crear una tarea, eliminarla y comprobar que ya no aparece en el listado ni
puede consultarse por detalle.

**API Test**: Solicitar la eliminación de una tarea existente y después listar o consultar esa
tarea; el resultado debe confirmar que ya no está disponible.

**Web UI Test**: Desde la interfaz, eliminar una tarea visible y comprobar que desaparece del
listado sin afectar a las demás.

**Acceptance Scenarios**:

1. **Given** que existe una tarea, **When** el usuario la elimina, **Then** deja de aparecer en la
   lista.
2. **Given** que se intenta eliminar una tarea inexistente, **When** se solicita la eliminación,
   **Then** el sistema informa que no existe sin alterar otras tareas.

---

### Edge Cases

- Intento de crear o actualizar una tarea con título vacío o compuesto solo por espacios.
- Creación de una tarea sin descripción, que debe ser válida y mostrarse sin texto de descripción.
- Consulta, actualización, completado o eliminación de una tarea inexistente.
- Listado cuando no hay tareas registradas.
- Varias tareas con el mismo título, que deben tratarse como registros distintos.
- Una tarea ya completada se marca de nuevo como completada sin duplicarse ni cambiar su fecha de
  creación.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST permitir crear una tarea con título obligatorio y descripción opcional.
- **FR-002**: El sistema MUST asignar automáticamente estado pendiente y fecha de creación a cada
  tarea nueva.
- **FR-003**: El sistema MUST rechazar la creación o actualización de tareas sin título válido.
- **FR-004**: El sistema MUST listar todas las tareas disponibles mostrando, como mínimo, título,
  estado y fecha de creación.
- **FR-005**: El sistema MUST permitir consultar el detalle de una tarea individual por su
  identificador.
- **FR-006**: El sistema MUST permitir actualizar título y descripción de una tarea existente.
- **FR-007**: El sistema MUST permitir cambiar el estado de una tarea de pendiente a completada.
- **FR-008**: El sistema MUST permitir eliminar una tarea existente.
- **FR-009**: El sistema MUST informar claramente cuando una tarea solicitada no existe.
- **FR-010**: El sistema MUST ofrecer una interfaz web simple para ejecutar todas las acciones del
  ciclo CRUD.
- **FR-011**: El sistema MUST ofrecer validación equivalente de las mismas acciones mediante HTTP.
- **FR-012**: El sistema MUST mantener separadas las áreas de trabajo del frontend y del backend.
- **FR-013**: El sistema MUST NOT requerir usuarios, inicio de sesión, permisos ni autenticación.

### Key Entities *(include if feature involves data)*

- **Tarea**: Elemento gestionable de la lista. Contiene identificador, título obligatorio,
  descripción opcional, estado pendiente o completada, y fecha de creación.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Un usuario puede crear una tarea válida y verla en la lista en menos de 30 segundos.
- **SC-002**: El 100% de las acciones principales (crear, listar, consultar detalle, actualizar,
  completar y eliminar) pueden verificarse tanto desde la interfaz web como desde validaciones HTTP.
- **SC-003**: El 100% de los intentos con título vacío son rechazados con un mensaje comprensible
  sin crear ni modificar tareas.
- **SC-004**: Después de completar o eliminar una tarea, el listado refleja el cambio en la
  siguiente consulta del usuario.
- **SC-005**: Una persona nueva puede demostrar el flujo completo de una tarea en menos de
  5 minutos siguiendo las instrucciones del proyecto.

## Assumptions

- La POC está pensada para uso local o de demostración, no para uso multiusuario productivo.
- No hay cuentas, propiedad de tareas, permisos ni autenticación.
- Las tareas se conservan localmente durante la ejecución o entre ejecuciones según se decida en
  planificación, sin depender de servicios externos.
- La fecha de creación se genera al crear la tarea y no cambia cuando se edita o completa.
- La descripción puede estar vacía y no impide crear ni actualizar una tarea.
