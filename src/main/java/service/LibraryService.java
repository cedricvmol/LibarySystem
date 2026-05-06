package service;

import domain.Book;
import domain.BookCopy;
import domain.Member;

import java.util.Collection;
import java.util.Optional;

public class LibraryService {
    private MemberService memberService;
    private BookService bookService;
    private LoanService loanService;

    public LibraryService(MemberService memberService, BookService bookService, LoanService loanService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void addBook(String isbn, String title, String genre, String author, String language, String publisher, int loanPeriodDays) {
        bookService.addBook(new Book(isbn, title, genre, author, language, publisher, loanPeriodDays));
    }

    public void addBook(String isbn, String title, String genre, String author, String language, String publisher) {
        bookService.addBook(new Book(isbn, title, genre, author, language, publisher, 14));
    }

    public void addCopies(String isbn, int amount) {
        bookService.addCopies(isbn, amount);
    }

    public Collection<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public Book getBook(String isbn) {
        return bookService.getBook(isbn);
    }

    public Collection<BookCopy> getAllCopies(String isbn) {
        return bookService.getAllCopiesForBook(isbn);
    }

    public void removeBook(String isbn) {
        bookService.removeBook(isbn);
    }


    public void registerMember(String memberId, String password, String name, String address, String email, String phone) {
        memberService.registerMember(new Member(memberId, password, name, address, email, phone));
    }

    public Optional<Member> getMember(String memberId) {
        return memberService.getMember(memberId);
    }

    public Collection<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    public void removeMember(String memberId) {
        memberService.removeMember(memberId);
    }

    public void borrowBook(Member member, String isbn){
        loanService.borrowBook(member,isbn);
    }


}
