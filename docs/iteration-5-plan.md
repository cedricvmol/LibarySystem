# Iteration 5 – Reservations & Sorting

## Goal
Add the full reservation system and introduce sorting via `Comparable` and `Comparator`.

---

## New concepts: Comparable & Comparator

### Comparable
Implement on a domain class to give it a *natural ordering*. Java's `sorted()` (no argument) uses it automatically.

```java
public class Book implements Comparable<Book> {
    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }
}
```

### Comparator
For external or custom ordering — without modifying the class. Pass to `sorted()`:

```java
.sorted(Comparator.comparing(BookLoan::getDueDate))
```

Use `Comparable` when there's one obvious natural order. Use `Comparator` for everything else.

---

## What to build

### Domain
- `ReservationStatus` enum — PENDING, CANCELLED, FULFILLED
- `Reservation` class — reservationId, member, book, reservationDate, status (always starts PENDING)

### Service layer
- `ReservationService` — createReservation (block if any copy AVAILABLE), cancelReservation,
  getPendingReservationsForMember, getAllPendingReservations, fulfillNextReservation(isbn)
- `LoanService.returnBook(loanId)` — mark returned, set returnDate, mark copy AVAILABLE, return ISBN
- `LibraryService.returnBook(loanId)` — calls loanService.returnBook(), then
  reservationService.fulfillNextReservation(isbn); keeps the two services decoupled

### Sorting
- `Book` implements `Comparable<Book>` — natural order by title; applied in `viewBooks()`
- Loans sorted by dueDate — `Comparator.comparing(BookLoan::getDueDate)` in `getActiveLoansForMember()` and `getAllActiveLoans()`
- Reservations sorted by reservationDate — `Comparator.comparing(Reservation::getReservationDate)` in `getAllPendingReservations()` and `getPendingReservationsForMember()`; also used in `fulfillNextReservation` to guarantee FIFO order

### UI
- `MemberUI`: [4] Reserve a book, [5] View my reservations, [6] Cancel a reservation
- `LibrarianUI` Loans submenu: [2] Process return, [3] View all pending reservations

### Tests & seed data
- `ReservationServiceTest` — 5 test cases
- Expand seed data — 2 books with all copies pre-borrowed

---

## Definition of done
- Member can reserve, view, and cancel reservations
- Librarian can process a return and view pending reservations
- Return auto-fulfills the oldest PENDING reservation for that ISBN
- Books display sorted alphabetically, loans sorted by due date