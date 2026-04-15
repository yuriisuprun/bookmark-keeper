# Library App

A modern library application with Spring Boot backend and React frontend.

## Architecture

- **Backend**: Spring Boot 3.2.0 with JPA, H2 database, REST API
- **Frontend**: React with TypeScript, Vite
- **Database**: H2 (in-memory for development)
- **Java Version**: 21

## Setup

1. Ensure you have Java 21, Maven, and Node.js installed.

2. Install backend dependencies:
   ```
   cd backend
   mvn install
   ```

3. Install frontend dependencies:
   ```
   cd frontend
   npm install
   ```

## Running the Application

1. Start the backend:
   ```
   cd backend
   mvn spring-boot:run
   ```
   Backend will run on http://localhost:8080

2. Start the frontend:
   ```
   cd frontend
   npm run dev
   ```
   Frontend will run on http://localhost:5173

## API Endpoints

- GET /api/books - Get all books
- POST /api/books - Add a new book
- PUT /api/books/{id} - Update a book
- DELETE /api/books/{id} - Delete a book

## Features

- CRUD operations for books
- Simple UI to manage library books
