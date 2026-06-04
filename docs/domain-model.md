# Library System — Domain Model

## Classes

### Book
Represents a title in the catalog. One book can have many physical copies. Implements `Comparable<Book>` — natural ordering by title.

| Field | Type | Notes |
|-------|------|-------|
| isbn | String | Primary identifier |
| title | String | |
| author | String | |
| genre | String | |
| language | String | |
| publisher | String | |
| loanPeriodDays | int | Default 14; set per book by librarian |

---

### BookCopy
A physical copy of a book. Each copy has its own status.

| Field | Type | Notes |
|-------|------|-------|
| copyId | String | Generated (6-digit random) |
| book | Book | Reference to parent book |
| status | CopyStatus | AVAILABLE or BORROWED |

---

### CopyStatus *(enum)*
```
AVAILABLE
BORROWED
```

---

### Member
A registered library member.

| Field | Type | Notes |
|-------|------|-------|
| memberId | String | Primary identifier |
| name | String | |
| address | String | |
| email | String | |
| phone | String | |
| password | String | Plain text for now |

---

### BookLoan
Represents a single borrowing event.

| Field | Type | Notes |
|-------|------|-------|
| loanId | String | Generated |
| copy | BookCopy | The specific copy borrowed |
| member | Member | Who borrowed it |
| loanDate | LocalDate | Date issued |
| dueDate | LocalDate | loanDate + book.loanPeriodDays |
| returnDate | LocalDate | Null until returned |
| fee | double | 0 until returned late |
| returned | boolean | False until return processed |

**On return:** if returnDate > dueDate, fee = (days late) × daily rate.

---

### Reservation
A member's request for a book title (not a specific copy).

| Field | Type | Notes |
|-------|------|-------|
| reservationId | String | Generated |
| member | Member | Who reserved |
| book | Book | Which title (not a copy) |
| reservationDate | LocalDate | When reserved |
| status | ReservationStatus | PENDING → FULFILLED or CANCELLED |

**Auto-fulfil:** when a copy is returned, LoanService checks for the oldest
PENDING reservation on that book and marks it FULFILLED.

---

### ReservationStatus *(enum)*
```
PENDING
FULFILLED
CANCELLED
```

---

## Relationships

```
Book ──< BookCopy          one book, many copies
Book ──< Reservation       one book, many reservations
Member ──< BookLoan        one member, many loans
Member ──< Reservation     one member, many reservations
BookCopy ──< BookLoan      one copy, many loans (over time, one active at a time)
```

---

## Key invariants
- A copy can only have one active loan at a time (status = BORROWED → no new loan)
- A book cannot be removed if any of its copies have an active loan
- Overdue fee daily rate is a constant in LoanService (e.g. 0.25 per day)
- A member can reserve a book even if copies are available — UI warns them
