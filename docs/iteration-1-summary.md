# Iteration 1 – Librarian Book CRUD

## What was built

- Added `loanPeriodDays` field to `Book` with two constructors — 7-param (explicit) and 6-param (defaults to 14)
- Implemented `BookService.getBook()`, `getAllBooks()`, `getAllCopiesForBook()`, `removeBook()`
- Overloaded `addBook()` in both `BookService` and `LibraryService` to optionally accept `loanPeriodDays`
- Added all missing `LibraryService` wrapper methods (`getBook`, `getAllBooks`, `getAllCopies`, `removeBook`)
- Completed all book menu options in `LibraryApp`: view all books (formatted table), search by ISBN (book details + copy list with status), remove book (with confirmation), loan period prompt when adding a book
- Added test data (`addTestData`) with 10 books and copies, mixing default and custom loan periods
- JUnit 5 unit tests for all `BookService` methods — 10 tests covering happy paths and edge cases

## Key decisions

- `removeBook` blocks if any copy has status `BORROWED` — this is a placeholder using `CopyStatus` since `LoanService` doesn't exist yet; will be replaced with a real loan check in iteration 3
- Two-phase removal in `removeBook`: first loop checks for BORROWED copies, second loop collects IDs to remove — avoids `ConcurrentModificationException`
- `LibraryApp` calls only through `LibraryService`; `BookService` is never touched directly from the UI layer
- Overloaded `addBook` kept in both `BookService` and `LibraryService` — 6-param version stays for test data convenience

## Concepts practiced

- **Method overloading** — applied to regular methods (not just constructors), same pattern as `Book`'s constructors
- **ConcurrentModificationException** — understood why you can't remove from a collection while iterating it; fixed with a two-pass approach
- **assertThrows with lambdas** — introduced for exception testing in JUnit 5; first use of lambda syntax
- **Facade pattern** — `LibraryService` as the single entry point for all UI calls; UI never reaches into individual services directly

## Known limitations / next steps

- Active loan check in `removeBook` is a placeholder — real integration with `LoanService` comes in iteration 3
- Book search is ISBN-only; full text search (title/author/genre) is planned for iteration 3
- No actor selection yet — app starts directly in librarian book menu (iteration 2)
- `copyIdGenerator` has no collision detection — works fine at small scale, not production-safe
