package app;

import service.BookService;
import service.LibraryService;
import service.LoanService;
import service.MemberService;

public class LibraryApp {

    private static MemberService memberService = new MemberService();
    private static LoanService loanService = new LoanService();
    private static BookService bookService = new BookService();
    LibraryService libraryService = new LibraryService(memberService,bookService,loanService);

    public static void main(String[] args){


        menu();
    }

    public static void menu(){

    }
}
