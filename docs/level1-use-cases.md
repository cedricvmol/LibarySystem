# Level 1 — Use Cases

## UC1 — Add a Book
**Actor:** Librarian
**Steps:**
1. Enter ISBN, title, author, genre, language, publisher
2. System checks ISBN is not already registered
3. Book is added to the catalog

## UC2 — Add Copies to a Book
**Actor:** Librarian
**Steps:**
1. Select a book by ISBN
2. Enter how many copies to add
3. System creates that many BookCopy objects linked to the book (status: AVAILABLE)

## UC3 — Register a Member
**Actor:** Librarian
**Steps:**
1. Enter name, address, email, phone
2. System generates a unique member ID
3. Member is added to the system

## UC4 — Unregister a Member
**Actor:** Librarian
**Steps:**
1. Find member by ID or name
2. Confirm removal
3. Member is removed (only if they have no active loans)

## UC5 — Borrow a Book Copy
**Actor:** Librarian
**Steps:**
1. Select a member
2. Select a book by ISBN
3. System checks if an AVAILABLE copy exists
4. Enter loan duration (in days)
5. System creates a BookLoan with loanDate = today, dueDate = today + duration
6. BookCopy status set to BORROWED

## UC6 — Return a Book Copy
**Actor:** Librarian
**Steps:**
1. Find the active loan (by member or copy)
2. System sets returnDate = today, returned = true
3. BookCopy status set back to AVAILABLE
4. Fee calculated and stored on the loan (Level 2 will display it)