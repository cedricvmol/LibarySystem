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
}
