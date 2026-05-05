# Iteration 3 — Member Menu & Loan Processing

## Goal
Build out the full member experience: book search, borrowing a book, and viewing
active loans. Introduces lambdas and `Predicate<T>` as a new concept.

---

## New concept: Lambdas + Predicate

A `Predicate<Book>` is a functional interface — it takes a `Book` and returns a
`boolean`. You write one using a lambda expression:

```java
Predicate<Book> byTitle = book -> book.getTitle().toLowerCase().contains(query);
```

You then call `predicate.test(book)` inside a loop to filter results.
In iteration 4, this same predicate slots straight into a Stream pipeline.

---

## What to build

### LoanService
- `borrowBook(Member member, String isbn)` — find an AVAILABLE copy for the given
  ISBN, create a `BookLoan` (loanDate = today, dueDate = today + book.loanPeriodDays),
  mark the copy as BORROWED. Throw if no available copy exists.
- `getActiveLoansForMember(String memberId)` — return all `BookLoan` entries where
  `member.getMemberId()` matches and `returned == false`.

### BookService — search
- `searchBooks(String query, String field)` — field is one of `"title"`, `"author"`,
  `"genre"`. Build a `Predicate<Book>` based on the field, then filter the books
  collection using a loop + `predicate.test(book)`.

### LibraryService
- `borrowBook(String memberId, String isbn)` — look up the member via `getMember()`,
  then delegate to `LoanService.borrowBook()`
- `getActiveLoansForMember(String memberId)` — delegate to `LoanService`
- `searchBooks(String query, String field)` — delegate to `BookService`

### MemberUI
Replace the placeholder menu with:
- `[1] Search books` — prompt for field + query, display results
- `[2] Loan a book` — prompt for ISBN, call borrowBook, confirm success
- `[3] View my active loans` — display open loans with due dates
- `[b] Back`

### Tests
- `LoanServiceTest` — cover: borrow successfully, borrow when no copy available,
  view active loans for member, active loans excludes returned loans.

---

## Definition of done
- Member can log in, search books, borrow a book, and view their active loans
- `Predicate<Book>` used in `searchBooks`
- `LoanService` methods covered by unit tests
