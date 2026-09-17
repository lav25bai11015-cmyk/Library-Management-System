# Library Management System

A command-line based Library Management System developed in Java using Object-Oriented Programming concepts.

## Overview

The Library Management System is designed to manage basic library operations such as users, books, borrowing, returning, searching, transaction history, and fine calculation.

The project demonstrates practical use of Java concepts including inheritance, abstraction, polymorphism, encapsulation, interfaces, generics, collections, exception handling, file I/O, and modular programming.

## Features

### User Management

- Admin and Member roles
- User authentication using email and password
- Regular and Premium membership types
- Role-based menus
- User profile display
- Duplicate email prevention

### Book Management

- Add physical books
- Add E-books
- Remove books
- View all books
- Search books by title or author
- Automatic book availability management
- Duplicate book ID prevention
- File-based book persistence

### Transaction Management

- Borrow books
- Return books
- Borrowing limits
- Transaction history
- Automatic return date tracking
- Overdue fine calculation

### Testing

The project includes automated functional tests for:

- Adding and finding books
- Duplicate book prevention
- Book searching
- Borrowing books
- Returning books
- Borrowing limits

## Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- Java File I/O
- Exception Handling
- Interfaces
- Generics
- Inheritance
- Polymorphism
- Encapsulation
- Git
- GitHub

## Project Structure

```text
Library-Management-System/
│
├── data/
│   └── books.dat
│
├── src/
│   ├── exceptions/
│   │   └── LibraryException.java
│   │
│   ├── interfaces/
│   │   ├── Reportable.java
│   │   └── Searchable.java
│   │
│   ├── models/
│   │   ├── Admin.java
│   │   ├── Book.java
│   │   ├── EBook.java
│   │   ├── Member.java
│   │   ├── PhysicalBook.java
│   │   ├── Transaction.java
│   │   └── User.java
│   │
│   ├── services/
│   │   ├── BookService.java
│   │   ├── TransactionService.java
│   │   └── UserService.java
│   │
│   ├── utils/
│   │   ├── FileHandler.java
│   │   └── ReportGenerator.java
│   │
│   └── Main.java
│
├── tests/
│   └── LibraryTest.java
│
├── statement.md
└── README.md