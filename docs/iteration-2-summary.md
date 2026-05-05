# Iteration 2 – Actor Selection & Member Management

## What was built
- Added `password` field to `Member` domain class (KAN-18)
- Startup actor selection: `[1] Librarian` / `[2] Member` prompt on launch (KAN-19)
- `MemberService` with `registerMember`, `getMember` (returns `Optional<Member>`), `getAllMembers`, `removeMember` (KAN-20–23)
- `LibraryService` delegation wrappers for all `MemberService` methods; constructs `Member` object before passing to service
- Member login flow: prompts memberId + password, validates via `getMember()` + `Optional` check (KAN-24)
- Librarian member management UI: register, view all, remove member (KAN-25–26)
- JUnit 5 unit tests for all `MemberService` methods (KAN-27)
- Refactored `BookService.addBook()` to accept a `Book` object; `LibraryService` now constructs the `Book` (KAN-28)

## Key decisions
- `getMember()` returns `Optional<Member>` instead of throwing or returning null — callers check `isPresent()` before accessing the value
- `registerMember` pattern applied consistently: LibraryService constructs the domain object, the service only stores it
- Active loan guard on `removeMember` deferred — method throws if ID not found but does not yet check for active loans

## Concepts practiced
- `Optional` — `getMember()` returns `Optional<Member>`; used with `isPresent()`, `isEmpty()`, and `.get()` throughout UI and tests
- JUnit 5 testing — full MemberService coverage: happy paths, exception cases, state verification after failed operations

## Known limitations / next steps
- Member menu is a stub — no search, loan, or view-loans functionality yet (iteration 3)
- `removeMember` does not guard against active loans (planned for a later iteration)
- Passwords stored as plain text
