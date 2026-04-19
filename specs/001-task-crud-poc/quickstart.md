# Quickstart: Task CRUD POC

## Prerequisites

- Java 21
- Maven 3.9+
- Node.js 20+
- npm

## Start Backend

```powershell
cd backend
mvn spring-boot:run
```

Expected backend URL:

```text
http://localhost:8080
```

## Start Frontend

In a second terminal:

```powershell
cd frontend
npm install
npm run dev
```

Expected frontend URL:

```text
http://localhost:5173
```

The frontend dev server proxies `/api` requests to `http://localhost:8080`.

## API Verification

Create a task:

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

List tasks:

```powershell
Invoke-RestMethod -Method Get -Uri http://localhost:8080/api/tasks
```

Get task detail:

```powershell
Invoke-RestMethod -Method Get -Uri http://localhost:8080/api/tasks/1
```

Update a task:

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

Complete a task:

```powershell
Invoke-RestMethod -Method Patch -Uri http://localhost:8080/api/tasks/1/complete
```

Delete a task:

```powershell
Invoke-WebRequest -UseBasicParsing -Method Delete -Uri http://localhost:8080/api/tasks/1
```

Validate required title:

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

Expected result: HTTP 400 with a clear validation message.

## Web UI Verification

### US1 Create And List

1. Open `http://localhost:5173`.
2. Create a task with a title and optional description.
3. Confirm the task appears in the list as pending with creation date.
4. Try to create a task with a blank title and confirm validation is shown.

### US2 Detail

1. Select a task from the list.
2. Confirm the detail panel shows title, description, status, and creation date.

### US3 Update And Complete

1. Select a pending task.
2. Click Edit, change title or description, and save.
3. Confirm the updated values appear in the list and detail panel.
4. Click Complete and confirm the status changes to completed.

### US4 Delete

1. Select a task from the list.
2. Click Delete.
3. Confirm the task disappears from the list and the detail panel resets.

## Test Commands

Backend tests:

```powershell
cd backend
mvn test
```

Frontend smoke check:

```powershell
cd frontend
npm run dev
```

Then complete the Web UI Verification steps above.
