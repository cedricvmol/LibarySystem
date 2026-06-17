package service;


import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    private BookService bookService;
    private LoanService loanService;
    private Member member,member2;
    private Book book,book2;


    @BeforeEach
    void setup(){
        bookService = new BookService(null,null);
        loanService = new LoanService(bookService,null);

        member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        member2 = new Member("US02", "admin", "Bob Smith", "456 Oak Avenue, Shelbyville", "bob.smith@email.com", "+1-555-876-5432");
        book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        book2 = new Book("9780316769174", "The Catcher in the Rye", "Classic", "J.D. Salinger", "English", "Little, Brown",21);

        bookService.addBook(book);
        bookService.addCopies("9780261103573",10);

        bookService.addBook(book2);
        bookService.addCopies("9780316769174",5);
    }


    @Test
    void testBorrowSuccessfully(){
        loanService.borrowBook(member,book.getIsbn());

        assertEquals(1,loanService.getActiveLoansForMember(member.getMemberId()).size());

        boolean foundBorrowedCopy = false;
        for (BookCopy copy : bookService.getAllCopiesForBook(book.getIsbn())){
            if(copy.getStatus().equals(CopyStatus.BORROWED)){
                foundBorrowedCopy = true;
            }
        }
        assertTrue(foundBorrowedCopy);
    }

    @Test
    void testBorrowNoAvailableCopy(){
        for(int i=0;i<10;i++){
            loanService.borrowBook(member,book.getIsbn());
        }

        assertThrows(IllegalArgumentException.class , () -> {
            loanService.borrowBook(member,book.getIsbn());
        });
    }

    @Test
    void getActiveLoansForMembers(){
        Book book1 = new Book("9780316769174", "The Catcher in the Rye", "Classic", "J.D. Salinger", "English", "Little, Brown",21);
        Book book2 = new Book("9782070360024", "Le Petit Prince", "Fable", "Antoine de Saint-Exupéry", "French", "Gallimard");
        Book book3 = new Book("9788423335305", "Cien años de soledad", "Magical Realism", "Gabriel García Márquez", "Spanish", "Editorial Sudamericana", 21);


        bookService.addBook(book2);
        bookService.addCopies(book2.getIsbn(),2);
        bookService.addBook(book3);
        bookService.addCopies(book3.getIsbn(),5);

        loanService.borrowBook(member,book.getIsbn());
        loanService.borrowBook(member,book1.getIsbn());
        loanService.borrowBook(member,book2.getIsbn());
        loanService.borrowBook(member,book3.getIsbn());

        assertEquals(4,loanService.getActiveLoansForMember(member.getMemberId()).size());
    }

    @Test
    void testActiveLoansExcludeReturnedLoans(){
        loanService.borrowBook(member,book.getIsbn());

        List<BookLoan> loans = loanService.getActiveLoansForMember(member.getMemberId());
        loans.getFirst().setReturned(true);

        assertEquals(0,loanService.getActiveLoansForMember(member.getMemberId()).size());
    }

    @Test
    void testGetAllActiveLoans(){
        loanService.borrowBook(member,book.getIsbn());
        loanService.borrowBook(member2,book2.getIsbn());

        assertEquals(2,loanService.getAllActiveLoans().size());
    }

    @Test
    void testGetAllActiveLoansExcludeReturnedLoans(){
        loanService.borrowBook(member,book.getIsbn());
        loanService.borrowBook(member2,book2.getIsbn());

        List<BookLoan> loans = loanService.getAllActiveLoans();
        loans.getFirst().setReturned(true);

        assertEquals(1,loanService.getAllActiveLoans().size());
    }

}
