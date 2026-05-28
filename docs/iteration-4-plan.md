# Iteration 4 – Streams API

## Goal
Replace existing for-loops with stream pipelines and add a new librarian feature
built entirely with streams. Introduces the Streams API as the primary new concept.

---

## New concept: Streams API

A stream is a pipeline: source → zero or more intermediate operations → one terminal operation.

```java
// Source
collection.stream()
    // Intermediate (lazy, can chain multiple)
    .filter(x -> x.isActive())
    .map(x -> x.getName())
    // Terminal (triggers execution, produces a result)
    .collect(Collectors.toList());
```

Key operations this iteration:

| Operation | Type | What it does |
|-----------|------|--------------|
| `filter(predicate)` | intermediate | keeps elements matching the predicate |
| `map(function)` | intermediate | transforms each element |
| `collect(Collectors.toList())` | terminal | gathers results into a List |
| `findFirst()` | terminal | returns `Optional<T>` — first match or empty |
| `forEach(consumer)` | terminal | runs an action on each element |

`filter` + `collect` replaces "loop + if + add to list".
`filter` + `findFirst` replaces "loop until found, return it".

---

## What to build

### Refactors (no behaviour change)

**BookService.searchBooks()**
Replace the `Predicate<Book>` + for-loop with a stream pipeline:
`stream().filter(predicate).collect(Collectors.toList())`

**LoanService.getActiveLoansForMember()**
Replace the for-loop with:
`stream().filter(...).collect(Collectors.toList())`

**LoanService.borrowBook()**
Replace the for-loop with `stream().filter().findFirst()`.
`findFirst()` returns `Optional<BookCopy>` — use `ifPresent` / `orElseThrow` to handle the result.

### New feature

**LoanService.getAllActiveLoans()**
Returns all non-returned loans. Single stream pipeline.

**LibraryService.getAllActiveLoans()**
Delegation wrapper.

**LibrarianUI — Loans submenu**
Add `[3] Loans` to the librarian menu, with one option:
`[1] View all active loans` — displays loan ID, member ID, book title, due date.

### Tests

**LoanServiceTest**
Add tests for `getAllActiveLoans()`:
- returns all active loans across multiple members
- excludes returned loans

---

## Definition of done
- All three refactors use stream pipelines (no for-loops in those methods)
- `getAllActiveLoans()` implemented and tested
- Librarian can view all active loans from the menu
