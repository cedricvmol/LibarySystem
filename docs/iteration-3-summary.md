# Iteration 3 – Member Menu & Loan Processing

## What was built
- `LoanService.borrowBook()` — finds the first AVAILABLE copy for an ISBN, creates a `BookLoan` with loanDate = today and dueDate = today + book's loanPeriodDays, marks the copy BORROWED; throws `IllegalArgumentException` if no copy is available
- `LoanService.getActiveLoansForMember()` — returns all loans where memberId matches and `returned == false`
- `BookService.searchBooks()` — builds a `Predicate<Book>` based on the field parameter (title/author/genre), filters the books collection using `predicate.test(book)`; throws `IllegalArgumentException` for unknown fields
- `LibraryService` delegation methods: `borrowBook()`, `getActiveLoansForMember()`, `searchBooks()`
- `LibraryService.removeMember()` guard — blocks removal if member has active loans
- `MemberUI` — full member flow: login, search books, loan a book, view active loans; each action is a static helper method that receives the `Member` object
- `LoanServiceTest` — 4 test cases covering borrow success, no available copy, active loans list, returned loans excluded

## Key decisions
- `Predicate<Book>` is built in a switch and then applied in a separate loop — keeps the filtering logic clean and sets up a natural transition to Streams in iteration 4
- `searchBooks` in `MemberUI` wraps the service call in a try-catch — added after noticing `BookService` throws for invalid field input, a gap that was originally missed
- `Member` object is threaded through UI helper methods rather than re-fetched from the service — avoids a redundant lookup since login already validated the member

## Concepts practiced
- **Lambdas + `Predicate<T>`** (new) — used to build field-specific book filters; `predicate.test(book)` called in a loop
- **`IllegalArgumentException`** — thrown from service layer, caught and displayed in UI layer
- **`Optional`** — used in login flow to safely look up a member before checking the password
- **Static methods with shared state** — `member` passed as parameter instead of stored as class-level state

## Known limitations / next steps
- Storage is in-memory; loans are lost on restart (addressed in iteration 6)
- `viewActiveLoans` prints an empty table header when there are no active loans
- Login returns `null` on failure — could be refactored to `Optional<Member>` in a future cleanup
