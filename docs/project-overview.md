# Library System — Project Overview

## Domain
A CLI library management system with two distinct actors: a **Member** who
interacts with books and their own loans/reservations, and a **Librarian**
who administers the full system.

---

## Actors & Business Rules

### Member
- Logs in with member ID + password
- Can search books by title, author, or genre
- Can loan a book — system assigns an available copy; due date = today + book's loan period
- Can reserve a book (even if copies are available — system shows "a copy is already available")
- Can view their active loans and pending reservations
- Can cancel a reservation
- No limit on concurrent active loans

### Librarian
- Selects "Librarian" at startup — no password required
- Full read access: lookup any book, copy, loan, or member by ID
- Can add/remove books and copies
- Can register/remove members
- Cannot remove a book that has active loans (system blocks it)
- Processes returns — system auto-calculates overdue fee and auto-fulfills any pending reservation for that book

---

## Use Cases

| # | Actor | Action | Outcome |
|---|-------|--------|---------|
| 1 | Member | Log in with member ID + password | Member menu shown |
| 2 | Member | Search books by title / author / genre | Matching books with availability shown |
| 3 | Member | Loan a book (title → system picks copy) | Copy marked BORROWED, loan created with due date |
| 4 | Member | Reserve a book | Reservation created PENDING (message shown if copies available) |
| 5 | Member | View active loans | Open loans with due dates listed |
| 6 | Member | View my reservations | Pending reservations listed |
| 7 | Member | Cancel a reservation | Reservation marked CANCELLED |
| 8 | Librarian | Select librarian role | Admin menu shown |
| 9 | Librarian | Add a book | Book registered (with loan period in days) |
| 10 | Librarian | Add copies for a book | N copies created, status AVAILABLE |
| 11 | Librarian | Remove a book | Blocked if active loans exist; otherwise removed with all copies |
| 12 | Librarian | Lookup book by ISBN | Book details + all copies + status shown |
| 13 | Librarian | Register a member | Member added with ID + password |
| 14 | Librarian | Remove a member | Member removed |
| 15 | Librarian | Lookup member by ID | Member details + active loans + reservations shown |
| 16 | Librarian | Lookup loan by ID | Loan details shown |
| 17 | Librarian | Process a return (by loan ID) | Copy → AVAILABLE, fee calculated, pending reservation auto-fulfilled |
| 18 | Librarian | View all active loans | Full open loan list |
| 19 | Librarian | View all pending reservations | Reservation queue shown |

---

## Architecture

```
LibraryApp
├── LoginUI              ← startup: member login or librarian selection
├── MemberUI             ← member menu tree
├── LibrarianUI          ← librarian menu tree
└── LibraryService       ← single facade into all services
    ├── BookService
    ├── MemberService
    ├── LoanService
    └── ReservationService
```

All UI classes only do input/output. All business logic lives in services.
Domain classes have no console output.

---

## Iteration Plan

| # | Theme | New concept |
|---|-------|-------------|
| 1 | Librarian — complete book CRUD | None (foundation) | ✅ Done |
| 2 | Actor selection + member management (librarian) | `Optional` | ✅ Done |
| 3 | Member menu — search, loan, view loans | Lambdas + `Predicate` | ✅ Done |
| 4 | Streams API — power up all search and list views | Streams (filter, map, collect) | ✅ Done |
| 5 | Reservations + sorting | `Comparable` / `Comparator` | ✅ Done |
| 6 | SQLite persistence + overdue fees | Reinforcement |
