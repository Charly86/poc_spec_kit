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
curl.exe -i -X POST http://localhost:8080/api/tasks `
  -H "Content-Type: application/json" `
  --data "{`"title`":`"Comprar leche`",`"description`":`"Pasar por el supermercado`"}"
```

List tasks:

```powershell
curl.exe -i http://localhost:8080/api/tasks
```

Get task detail:

```powershell
curl.exe -i http://localhost:8080/api/tasks/1
```

Update a task:

```powershell
curl.exe -i -X PUT http://localhost:8080/api/tasks/1 `
  -H "Content-Type: application/json" `
  --data "{`"title`":`"Comprar pan`",`"description`":`"Actualizar la compra`"}"
```

Complete a task:

```powershell
curl.exe -i -X PATCH http://localhost:8080/api/tasks/1/complete
```

Delete a task:

```powershell
curl.exe -i -X DELETE http://localhost:8080/api/tasks/1
```

Validate required title:

```powershell
curl.exe -i -X POST http://localhost:8080/api/tasks `
  -H "Content-Type: application/json" `
  --data "{`"title`":`"   `"}"
```

Expected result: HTTP 400 with a clear validation message.

## Web UI Verification

1. Open `http://localhost:5173`.
2. Create a task with a title and optional description.
3. Confirm the task appears in the list as pending with creation date.
4. Open the task detail.
5. Edit title or description and save.
6. Mark the task as completed.
7. Delete the task and confirm it disappears from the list.
8. Try to create a task with a blank title and confirm validation is shown.

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
