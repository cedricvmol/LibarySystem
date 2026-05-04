package service;

import domain.Book;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryService {
    private MemberService memberService;
    private BookService bookService;
    private LoanService loanService;

    public LibraryService(MemberService memberService, BookService bookService, LoanService loanService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void addBook(String isbn, String title, String genre, String author, String language, String publisher) {
        bookService.addBook(isbn, title, genre, author, language, publisher);
    }

    public void addCopies(String isbn, int amount) {
        bookService.addCopies(isbn, amount);
    }

    public void removeBook(String isbn) {
        bookService.removeBook(isbn);
    }

    public Collection<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<Book> searchBooks(String query) {
        String q = query.toLowerCase();
        return bookService.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q)
                        || b.getAuthor().toLowerCase().contains(q)
                        || b.getIsbn().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public int getTotalCopiesCount(String isbn) {
        return bookService.getTotalCopiesCount(isbn);
    }

    public int getAvailableCopiesCount(String isbn) {
        return bookService.getAvailableCopiesCount(isbn);
    }
}
