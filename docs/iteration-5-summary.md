# Iteration 5 – Reservations & Sorting

## What was built
- Added `ReservationStatus` enum — PENDING, FULFILLED, CANCELLED
- Added `Reservation` domain class — reservationId, member, book, reservationDate, status
- Added `ReservationService` — createReservation (blocks if copy available), cancelReservation, getPendingReservationsForMember, getAllPendingReservations, fulfillNextReservation
- Added `LoanService.returnBook(loanId)` — marks loan returned, sets returnDate, marks copy AVAILABLE, returns ISBN
- `LibraryService.returnBook(loanId)` — delegates to LoanService then calls `fulfillNextReservation` to auto-fulfill the oldest PENDING reservation for that ISBN
- `Book` implements `Comparable<Book>` — natural ordering by title; applied in `viewBooks()`
- Loans sorted by due date using `Comparator.comparing(BookLoan::getDueDate)`
- Reservations sorted by reservationDate using `Comparator.comparing(Reservation::getReservationDate)` — ensures FIFO fulfillment
- MemberUI — added [4] Reserve a book, [5] View my reservations, [6] Cancel a reservation
- LibrarianUI loans submenu — added [2] Process return, [3] View all pending reservations
- `ReservationServiceTest` — 5 test cases covering createReservation (success + copy available), cancelReservation (success + wrong ID), and getPendingReservationsForMember isolation

## Key decisions
- `LibraryService.returnBook` calls `fulfillNextReservation` after the loan is processed — keeps `LoanService` and `ReservationService` decoupled; the facade owns the coordination
- `fulfillNextReservation` sorts by `reservationDate` before calling `findFirst()` to guarantee FIFO order regardless of insertion order
- `createReservation` blocks when a copy is AVAILABLE and throws `IllegalArgumentException` — UI catches and displays the message
- `cancelReservation` in the UI shows the pending list before asking for an ID — better UX than asking for an ID blind

## Concepts practiced
- `Comparable<T>` — implemented on `Book` for natural alphabetical ordering; used by `stream().sorted()`
- `Comparator.comparing(MethodReference)` — external ordering for loans (by due date) and reservations (by date); used where no single natural order exists
- `stream().sorted(...).findFirst()` — combining sort and find in `fulfillNextReservation` to guarantee FIFO

## Known limitations / next steps
- Reservation fulfillment only marks the status FULFILLED — no notification to the member
- No duplicate reservation guard — a member can reserve the same book multiple times
- Iteration 6 will replace in-memory storage with SQLite persistence