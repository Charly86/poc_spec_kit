# Task CRUD POC

Prueba de concepto de una lista de tareas con backend REST y frontend web desacoplado. El proyecto esta pensado para validar un CRUD simple de extremo a extremo, manteniendo una estructura de monorepo clara y sin complejidad de produccion innecesaria.

## Funcionalidad

La aplicacion permite:

- Crear tareas con titulo obligatorio y descripcion opcional.
- Listar tareas existentes.
- Consultar el detalle de una tarea.
- Actualizar titulo y descripcion.
- Marcar tareas como completadas.
- Eliminar tareas.

No hay usuarios, autenticacion ni roles.

## Tecnologias

Backend:

- Java 21
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 local

Frontend:

- Node.js 20+
- npm
- Vite
- HTML, CSS y JavaScript nativo

## Estructura Del Repositorio

```text
backend/
  pom.xml
  src/main/java/com/example/taskcrud/
  src/main/resources/application.properties
  src/test/java/com/example/taskcrud/

frontend/
  package.json
  vite.config.js
  index.html
  src/

specs/001-task-crud-poc/
  spec.md
  plan.md
  contracts/openapi.yaml
  quickstart.md
  tasks.md
  test-plan.md
```

## Requisitos

- Java 21
- Maven 3.9+
- Node.js 20+
- npm

## Ejecucion Local

### 1. Arrancar El Backend

Desde la raiz del repositorio:

```powershell
cd backend
mvn spring-boot:run
```

La API queda disponible en:

```text
http://localhost:8080
```

La API REST se expone bajo:

```text
/api/tasks
```

### 2. Arrancar El Frontend

En otra terminal:

```powershell
cd frontend
npm install
npm run dev
```

La aplicacion web queda disponible en:

```text
http://localhost:5173
```

Durante desarrollo, Vite redirige las llamadas a `/api` hacia `http://localhost:8080`.

## Validacion Por API

Con el backend arrancado, se puede validar el flujo CRUD desde PowerShell.

Crear una tarea:

```powershell
$createBody = @{
  title = "Comprar leche"
  description = "Pasar por el supermercado"
} | ConvertTo-Json -Compress

Invoke-RestMethod -Method Post `
  -Uri http://localhost:8080/api/tasks `
  -ContentType "application/json" `
  -Body $createBody
```

Listar tareas:

```powershell
Invoke-RestMethod -Method Get -Uri http://localhost:8080/api/tasks
```

Consultar detalle:

```powershell
Invoke-RestMethod -Method Get -Uri http://localhost:8080/api/tasks/1
```

Actualizar:

```powershell
$updateBody = @{
  title = "Comprar pan"
  description = "Actualizar la compra"
} | ConvertTo-Json -Compress

Invoke-RestMethod -Method Put `
  -Uri http://localhost:8080/api/tasks/1 `
  -ContentType "application/json" `
  -Body $updateBody
```

Completar:

```powershell
Invoke-RestMethod -Method Patch -Uri http://localhost:8080/api/tasks/1/complete
```

Eliminar:

```powershell
Invoke-WebRequest -UseBasicParsing -Method Delete -Uri http://localhost:8080/api/tasks/1
```

Validar titulo obligatorio:

```powershell
$invalidBody = @{ title = "   " } | ConvertTo-Json -Compress

try {
  Invoke-RestMethod -Method Post `
    -Uri http://localhost:8080/api/tasks `
    -ContentType "application/json" `
    -Body $invalidBody
} catch {
  $_.Exception.Response.StatusCode.value__
}
```

El resultado esperado es `400`.

## Validacion Por Interfaz Web

Con backend y frontend arrancados:

1. Abrir `http://localhost:5173`.
2. Crear una tarea con titulo y descripcion opcional.
3. Confirmar que aparece en la lista como pendiente.
4. Seleccionar la tarea y revisar el detalle.
5. Editar titulo o descripcion y guardar.
6. Marcarla como completada.
7. Eliminarla y confirmar que desaparece de la lista.
8. Intentar crear una tarea sin titulo y confirmar que se muestra validacion.

## Tests Y Build

Backend:

```powershell
cd backend
mvn test
```

Frontend:

```powershell
cd frontend
npm install
npm run build
```

## Despliegue Local De La POC

Este proyecto no incluye empaquetado Docker ni despliegue cloud. Para desplegarlo localmente:

1. Arrancar el backend con `mvn spring-boot:run`.
2. Arrancar el frontend con `npm run dev`.
3. Acceder a `http://localhost:5173`.

Para generar los artefactos del frontend:

```powershell
cd frontend
npm run build
```

El resultado queda en `frontend/dist/`.

## Documentacion Spec Kit

La documentacion funcional y tecnica de la feature esta en:

- `specs/001-task-crud-poc/spec.md`
- `specs/001-task-crud-poc/plan.md`
- `specs/001-task-crud-poc/contracts/openapi.yaml`
- `specs/001-task-crud-poc/quickstart.md`
- `specs/001-task-crud-poc/test-plan.md`
