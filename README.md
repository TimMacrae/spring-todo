# 📝 TodoJava Spring Boot Application

A simple Todo application built with Java, Spring Boot, and MongoDB.
It supports creating, updating, deleting, and undoing changes to todos via HTTP endpoints.

## ✨ Features

- Create, update, delete todos
- Undo the last change (multiple undos supported)
- RESTful API endpoints
- Integration and unit tests

## 🔗 Endpoints

- `GET /api/todo` — List all todos
- `POST /api/todo` — Create a new todo
- `GET /api/todo/{id}` — Get a todo by ID
- `PUT /api/todo/{id}` — Update a todo
- `DELETE /api/todo/{id}` — Delete a todo
- `POST /api/todo/undo` — Undo the last change

## 🧪 Testing

This project includes unit and integration tests to ensure reliability:

- TodoServiceTest — Tests core todo logic and service methods.
- ChangeHistoryServiceTest — Verifies undo functionality for changes.
- TodoTest — Checks the Todo model's data integrity.
- TodoDtoTest — Validates the TodoDto data transfer object.
- TodoNotFoundTest — Ensures correct exception for missing todos.
- TodoCouldNotBeCreatedTest — Ensures correct exception for creation failures.
- TodoControllerTest — Integration tests for API endpoints and database interactions.

##  🤖 ChatGPT Spellcheck Integration

This project integrates with the OpenAI ChatGPT API to check the spelling of todo items.

### Usage

- The `ChatGptService` sends todo descriptions to the ChatGPT API for spellchecking.
- If the API call fails, a `ChatGptSpellingRequestException` is thrown.
- If the spellcheck response is null, a `ChatGptSpellcheckIsNull` exception is thrown.