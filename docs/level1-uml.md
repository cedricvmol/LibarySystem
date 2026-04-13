# Level 1 — UML

## Domain Classes

```
┌─────────────────────────┐
│          Book           │
├─────────────────────────┤
│ - isbn: String          │
│ - title: String         │
│ - author: String        │
│ - genre: String         │
│ - language: String      │
│ - publisher: String     │
├─────────────────────────┤
│ + getters               │
└─────────────────────────┘
           │
           │ 1 to many
           ▼
┌─────────────────────────┐
│        BookCopy         │
├─────────────────────────┤
│ - copyId: String        │
│ - book: Book            │
│ - status: CopyStatus    │
├─────────────────────────┤
│ + getters               │
│ + setStatus()           │
└─────────────────────────┘
           │
           │ 1 to many
           ▼
┌─────────────────────────┐
│        BookLoan         │
├─────────────────────────┤
│ - loanId: String        │
│ - copy: BookCopy        │
│ - member: Member        │
│ - loanDate: LocalDate   │
│ - dueDate: LocalDate    │
│ - returnDate: LocalDate │
│ - fee: double           │
│ - returned: boolean     │
├─────────────────────────┤
│ + getters               │
│ + setReturnDate()       │
│ + setFee()              │
│ + setReturned()         │
└─────────────────────────┘

┌─────────────────────────┐
│         Member          │
├─────────────────────────┤
│ - memberId: String      │
│ - name: String          │
│ - address: String       │
│ - email: String         │
│ - phone: String         │
├─────────────────────────┤
│ + getters               │
└─────────────────────────┘

┌─────────────────────────┐
│       CopyStatus        │  (enum)
├─────────────────────────┤
│ AVAILABLE               │
│ BORROWED                │
└─────────────────────────┘
```

## Service Layer

```
┌─────────────────────────────────────────────────────┐
│                   LibraryService                    │
│              (coordinator — app talks only here)    │
├─────────────────────────────────────────────────────┤
│ - memberService: MemberService                      │
│ - bookService: BookService                          │
│ - loanService: LoanService                          │
└─────────────────────────────────────────────────────┘
        │               │               │
        ▼               ▼               ▼
┌─────────────┐ ┌─────────────┐ ┌─────────────┐
│MemberService│ │ BookService │ │ LoanService │
└─────────────┘ └─────────────┘ └─────────────┘
```

## Package Structure

```
src/
  app/
    LibraryApp.java
  service/
    LibraryService.java
    MemberService.java
    BookService.java
    LoanService.java
  domain/
    Member.java
    Book.java
    BookCopy.java
    BookLoan.java
    CopyStatus.java
  storage/
    DatabaseManager.java
```