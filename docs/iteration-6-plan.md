# Iteration 6 – SQLite Persistence & Overdue Fees

## Goal
Replace the in-memory storage with a SQLite database and implement overdue fee
calculation on return. No new Java concepts — pure reinforcement of JDBC patterns
already learned in BankApp.

---

## Overdue fee calculation

Add to `LoanService.returnBook()`:
- If `returnDate` is after `dueDate`, calculate `overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate)`
- `fee = overdueDays * 0.50` (€0.50 per day)
- Set the fee on the loan via `setFee()`
- Librarian sees the fee printed after processing a return

---

## Database layer

### DatabaseManager
- Holds the `Connection`, opens it in the constructor via `DriverManager.getConnection("jdbc:sqlite:library.db")`
- `initTables()` — runs `CREATE TABLE IF NOT EXISTS` for all 5 tables in FK-safe order:
  `books` → `members` → `copies` → `loans` → `reservations`
- Used once at startup in `LibraryApp`

### DAO classes (one per entity)
Create a `storage` package alongside `service` and `domain`.

| Class | Responsibility |
|---|---|
| `BookDao` | save(Book), findByIsbn(isbn), findAll(), delete(isbn) |
| `MemberDao` | save(Member), findById(id), findAll(), delete(id) |
| `CopyDao` | save(BookCopy, isbn), findAllForBook(isbn), updateStatus(copyId, status) |
| `LoanDao` | save(BookLoan), findById(id), findAllActive(), findActiveForMember(memberId), updateReturn(loanId, returnDate, fee) |
| `ReservationDao` | save(Reservation), findPendingForBook(isbn), findPendingForMember(memberId), findAllPending(), updateStatus(reservationId, status) |

### Wiring into services
Each service receives its DAO(s) via constructor injection. Replace the in-memory
`ArrayList` with DAO calls. `LibraryApp` constructs `DatabaseManager`, DAOs, then
services — in that order.

### Loading considerations
- `CopyDao` must resolve the `Book` reference when loading — receive an already-loaded book map
- `LoanDao` similarly needs resolved `BookCopy` and `Member` objects
- `ReservationDao` needs resolved `Book` and `Member` objects
- Load in order: books → members → copies → loans → reservations

---

## Definition of done
- App starts, creates `library.db` if it doesn't exist, tables initialised automatically
- All data survives a restart — books, members, copies, loans, and reservations
- `returnBook()` calculates and stores the overdue fee; librarian sees it printed
