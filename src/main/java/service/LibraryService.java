package service;

import domain.Book;
import domain.BookCopy;

import java.util.Collection;

public class LibraryService {
    private MemberService memberService;
    private BookService bookService;
    private LoanService loanService;

    public LibraryService(MemberService memberService, BookService bookService, LoanService loanService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void addBook(String isbn,String title,String genre,String author,String language,String publisher,int loanPeriodDays){
        bookService.addBook(isbn,title,genre,author,language,publisher,loanPeriodDays);
    }
    public void addBook(String isbn,String title,String genre,String author,String language,String publisher){
        bookService.addBook(isbn,title,genre,author,language,publisher,14);
    }

    public void addCopies(String isbn,int amount){
        bookService.addCopies(isbn,amount);
    }

    public Collection<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    public Book getBook(String isbn){
        return bookService.getBook(isbn);
    }

    public Collection<BookCopy> getAllCopies(String isbn){
        return bookService.getAllCopiesForBook(isbn);
    }
}
