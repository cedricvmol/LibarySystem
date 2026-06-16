package service;

import domain.Book;
import domain.Member;
import domain.Reservation;
import domain.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private BookService bookService;
    private Member member,member2;
    private LoanService loanService;
    private Book book,book2,book3;

    @BeforeEach
    void setUp(){
        bookService = new BookService(null,null);
        loanService = new LoanService(bookService);
        reservationService = new ReservationService(bookService);


        member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        member2 = new Member("US02", "admin", "Bob Smith", "456 Oak Avenue, Shelbyville", "bob.smith@email.com", "+1-555-876-5432");

        book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        book2 = new Book("9780316769174", "The Catcher in the Rye", "Classic", "J.D. Salinger", "English", "Little, Brown",21);
        book3 = new Book("9780062315007", "Sapiens", "Non-Fiction", "Yuval Noah Harari", "English", "Harper Perennial");

        bookService.addBook(book);
        bookService.addCopies("9780261103573",1);
        loanService.borrowBook(member,"9780261103573");

        bookService.addBook(book3);
        bookService.addCopies(book3.getIsbn(),1);
        loanService.borrowBook(member,book3.getIsbn());

        bookService.addBook(book2);
        bookService.addCopies("9780316769174",2);
    }


    @Test
    void testCreateReservationSuccessfully(){
        reservationService.createReservation(member2,"9780261103573");

        assertEquals(1,reservationService.getAllPendingReservations().size());
    }

    @Test
    void testCreateReservationCopyAvailable(){

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(member2,"9780316769174");
        });
    }

    @Test
    void testCancelReservation(){
        reservationService.createReservation(member2,"9780261103573");
        Reservation reservation = reservationService.getPendingReservationsForMember(member2.getMemberId()).getFirst();
        reservationService.cancelReservation(reservation.getReservationId());

        assertEquals(0,reservationService.getAllPendingReservations().size());
        assertEquals(ReservationStatus.CANCELLED,reservation.getReservationStatus());
    }

    @Test
    void testCancelReservationWrongId(){
        reservationService.createReservation(member2,"9780261103573");

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservation("RandomID");
        });
    }

    @Test
    void getPendingReservationsForMember(){
        reservationService.createReservation(member2,book.getIsbn());
        reservationService.createReservation(member,book3.getIsbn());

        assertEquals(1,reservationService.getPendingReservationsForMember(member2.getMemberId()).size());
    }



}
