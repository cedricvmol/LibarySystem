# Iteration 4 – Streams API

## What was built
- Refactored `BookService.searchBooks()` to use `stream().filter(predicate).collect(Collectors.toList())`
- Refactored `LoanService.getActiveLoansForMember()` to a stream pipeline
- Refactored `LoanService.borrowBook()` to use `stream().filter().findFirst().orElseThrow()`
- Added `LoanService.getAllActiveLoans()` — returns all non-returned loans via stream pipeline
- Added `LibraryService.getAllActiveLoans()` delegation wrapper
- Added Loans submenu to `LibrarianUI` with `[1] View all active loans` (columns: loan ID, member ID, book title, due date)
- Added two `LoanServiceTest` test cases for `getAllActiveLoans()`

## Key decisions
- `orElseThrow(() -> new IllegalArgumentException(...))` used in `borrowBook()` instead of bare `orElseThrow()` — ensures the right exception type is thrown with a meaningful message rather than a generic `NoSuchElementException`
- `getAllActiveLoans()` placed on `LoanService` (not `LibraryService`) keeping business logic in the service layer; `LibraryService` just delegates per the facade pattern

## Concepts practiced
- `stream().filter().collect(Collectors.toList())` — replaces for-loop + if + add-to-list in two methods
- `stream().filter().findFirst()` — replaces for-loop with early return; returns `Optional<T>`
- `orElseThrow(Supplier)` — unwraps an Optional or throws a supplied exception
- Stream pipelines as a functional alternative to imperative loops

## Known limitations / next steps
- No sorting on the active loans view — iteration 5 will introduce `Comparable` / `Comparator`
- `getAllActiveLoans()` returns all loans system-wide; no pagination or filtering by date yet