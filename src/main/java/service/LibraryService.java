package service;

public class LibraryService {
    private MemberService memberService;
    private BookService bookService;
    private LoanService loanService;

    public LibraryService(MemberService memberService, BookService bookService, LoanService loanService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void addBook(String isbn,String title,String genre,String author,String language,String publisher){
        bookService.addBook(isbn,title,genre,author,language,publisher);
    }

    public void addCopies(String isbn,int amount){
        bookService.addCopies(isbn,amount);
    }
}
