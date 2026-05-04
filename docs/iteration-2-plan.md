# Iteration 2 — Actor Selection & Member Management

## Goal
Add actor selection at startup (Librarian vs Member login), implement a full
`MemberService`, and wire up the librarian's member management UI. Introduces
`Optional` as a new concept.

---

## New concept: Optional
`getMember()` returns `Optional<Member>` instead of throwing or returning null.
Teaches: avoiding null, `Optional.of`, `Optional.empty()`, `orElse`, `ifPresent`, `isPresent`.

---

## What to build

### Domain change (KAN-18)
- Add `String password` field to `Member` — getter + updated constructor

### MemberService (KAN-20, 21, 22, 23)
- `registerMember(Member member)` — store in HashMap by memberId, throw if duplicate
- `Optional<Member> getMember(String memberId)` — return `Optional.empty()` if not found
- `Collection<Member> getAllMembers()` — return all members
- `removeMember(String memberId)` — throw if not found; active loan check is a placeholder for now

### LibraryService
- Thin delegation wrappers for each MemberService method above

### UI — startup actor selection (KAN-19)
- On launch: prompt `[1] Librarian  [2] Member`
- Librarian → goes straight to existing librarian menu
- Member → triggers login flow

### UI — Member login (KAN-24)
- Prompt for memberId + password
- Use `LibraryService.getMember()` → validate password
- On failure: show error; on success: route to member menu (stub for now)

### UI — Librarian: member management (KAN-25, 26)
- Register new member: prompt all fields, call `LibraryService.registerMember()`
- Lookup member by ID: show member details
- Remove member: prompt ID, confirm, call `LibraryService.removeMember()`

### Tests (KAN-27)
- JUnit 5 unit tests for all `MemberService` methods

---

## Definition of done
- App starts with actor selection
- Librarian can register, look up, and remove members
- Member can log in (and sees a placeholder menu)
- `getMember()` uses `Optional` correctly throughout
- All `MemberService` methods covered by tests
