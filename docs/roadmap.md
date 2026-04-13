# LibrarySystem — Project Roadmap

## Project Structure

```
app/
  LibraryApp         — entry point, menus, Scanner, input/output

service/
  LibraryService     — coordinator, only thing the app talks to
  MemberService      — member logic
  BookService        — book and copy logic
  LoanService        — loan logic

domain/
  Member
  Book
  BookCopy
  BookLoan

storage/
  DatabaseManager    — SQLite persistence
```

---

## Level 1 — Core Application [ ]
**Concepts:** classes, constructors, ArrayList, Scanner, switch, layered architecture from the start

Features:
- Add a book
- Add copies to a book
- Register / unregister a member
- Borrow a book copy
- Return a book copy

---

## Level 2 — Fees, Availability & Loan Overview [ ]
**Concepts:** LocalDateTime, fee calculation, filtering collections

Features:
- Calculate and display late fee on return
- View available copies of a book
- View which member has which book (and vice versa)

---

## Level 3 — Unit Testing & Loan History [ ]
**Concepts:** JUnit 5, @BeforeEach, AAA pattern, loan history tracking

Features:
- Unit tests for all domain classes
- Full loan history per member

---

## Level 4 — Database Storage with SQLite [ ]
**Concepts:** JDBC, PreparedStatement, ResultSet, try-with-resources, relational schema

Features:
- Persist all data to SQLite
- Load on startup, save on changes
- Schema: members, books, book_copies, book_loans

---

## Level 5 — Design Patterns [ ]
**Concepts:** Repository pattern, TBD second pattern

Features:
- Repository abstraction over DatabaseManager
- Cleaner separation between storage and service layer

---

## Level 6 — Search & Filtering [ ]
**Concepts:** stream filtering, search by multiple criteria

Features:
- Search books by title, author, genre
- Filter available books
- Filter loans by member or book