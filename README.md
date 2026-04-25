# Library App

Library management app with a Spring Boot backend and a React + TypeScript frontend.

## Stack

- Backend: Spring Boot `3.2.0`, Java `21`, Spring Web, Spring Data JPA, Flyway, H2
- Frontend: React `18.2`, Vite `5` (TypeScript), Axios

## Requirements

- Java 21
- Maven
- Node.js + npm

## Run Locally (Development)

1. Start the backend (dev profile):

   ```powershell
   .\scripts\run-backend.ps1
   ```

   Alternative (no scripts):

   ```powershell
   cd backend
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

   Backend runs at `http://localhost:8080`.
   H2 console: `http://localhost:8080/h2-console`.

2. Start the frontend:

   ```powershell
   .\scripts\run-frontend.ps1
   ```

   Alternative (no scripts):

   ```powershell
   cd frontend
   npm install
   npm run dev
   ```

   Frontend runs at `http://localhost:5588` (see `frontend/vite.config.ts`).

## API

Base path: `http://localhost:8080/api/books`

- `GET /api/books` List all books
- `GET /api/books/{id}` Get book by id
- `POST /api/books` Create a book
- `PUT /api/books/{id}` Update a book
- `DELETE /api/books/{id}` Delete a book
- `GET /api/books/search?query=...` Search across title/author/isbn
- `GET /api/books/search/title?title=...`
- `GET /api/books/search/author?author=...`
- `GET /api/books/search/isbn?isbn=...`
- `GET /api/books/search/year?year=...`

Notes:
- CORS is currently configured to allow the Vite dev server origin `http://localhost:5588`.
- The frontend calls the backend directly at `http://localhost:8080` (no Vite proxy configured).

## Database + Migrations

- Flyway migrations live in `backend/src/main/resources/db/migration/`.
- Sample books are inserted via Flyway migration `V2__Insert_initial_books.sql` (and the app also has a startup data loader as a fallback).

Profiles:
- Default (`application.yml`): in-memory H2 (`jdbc:h2:mem:librarydb`).
- `dev` (`application-dev.yml`): file-based H2 at `backend/data/librarydb` for persistence across restarts.
- `prod` (`application-prod.yml`): file-based H2 at `/opt/library/data/librarydb`, H2 console disabled, management endpoints exposed on `http://localhost:8081/actuator`.

## Production (Local)

Build + run without helper scripts:

```powershell
cd backend
mvn clean package -DskipTests
java -jar .\target\library-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

There is also a helper script:

```powershell
.\scripts\start-backend-prod.ps1 -Build
```

Note: the script currently builds with Maven profile `prod` (`-Pprod`), but no Maven profile is defined in `backend/pom.xml`. If that fails, use the manual build command above.
