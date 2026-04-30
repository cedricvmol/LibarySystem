# Iteration 1 — Librarian Book CRUD

## Goal
A working app where a librarian can fully manage the book catalog:
add books, add copies, view the full catalog, look up a book by ISBN, and remove a book.

No actor selection yet. App starts directly in a librarian book menu.

---

## What to build

### Domain change
- Add `loanPeriodDays` (int) to `Book` — librarian sets it when adding a book (default suggestion: 14)
- Update `Book` constructor accordingly

### BookService — add missing methods
- `getBook(String isbn)` → returns `Book` or throws if not found
- `getAllBooks()` → returns all books (as a Collection or List)
- `getAllCopiesForBook(String isbn)` → returns copies for that book
- `removeBook(String isbn)` → removes book + all its copies (active loan check comes in iteration 3)

### LibraryService — expose new methods
Thin delegation to BookService for each of the above.

### UI — complete the books menu
Wire up the stubs that already exist in `LibraryApp`:

| Menu option | What it does |
|-------------|--------------|
| View all books | Print all books, formatted table |
| Search books | Search by ISBN only for now (full search comes in iteration 3) |
| Add new book | Already works — add loanPeriodDays input |
| Add copies | Already works |
| Remove book | Call removeBook, print confirmation |

---

## Definition of done
- Librarian can add a book (with loan period), add copies, see the full catalog,
  look up by ISBN (shows book info + all copies + their status), and remove a book
- App compiles and runs without errors
- All menu options respond (no silent stubs)
