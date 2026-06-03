package service;

import domain.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LibraryService {
    private MemberService memberService;
    private BookService bookService;
    private LoanService loanService;
    private ReservationService reservationService;

    public LibraryService(MemberService memberService, BookService bookService, LoanService loanService,ReservationService reservationService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.loanService = loanService;
        this.reservationService = reservationService;
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
        if(!loanService.getActiveLoansForMember(memberId).isEmpty()){
            throw new IllegalStateException("Member has still an active loan.");
        }
        memberService.removeMember(memberId);

    }

    public void borrowBook(String memberId, String isbn){
        Optional<Member> result = memberService.getMember(memberId);
        if(result.isEmpty()){
           throw new IllegalArgumentException("The member does not exist.");
        }
        loanService.borrowBook(result.get(),isbn);
    }

    public List<BookLoan> getActiveLoansForMember(String memberId){
        return loanService.getActiveLoansForMember(memberId);
    }

    public List<BookLoan> getAllActiveLoans(){
        return loanService.getAllActiveLoans();
    }

    public Collection<Book> searchBooks(String query , String field){
        return bookService.searchBooks(query,field);
    }

    public void createReservation(String memberId, String isbn){
        Optional<Member> result = memberService.getMember(memberId);
        if(result.isEmpty()){
            throw new IllegalArgumentException("The member does not exist.");
        }
        reservationService.createReservation(result.get(),isbn);
    }

    public void cancelReservation(String reservationId){
        reservationService.cancelReservation(reservationId);
    }

    public List<Reservation> getPendingReservationsForMember(String memberId){
        return reservationService.getPendingReservationsForMember(memberId);
    }

    public List<Reservation> getAllPendingReservations(){
        return reservationService.getAllPendingReservations();
    }

    public void returnBook(String loanId){
        reservationService.fulfillNextReservation(loanService.returnBook(loanId));
    }

    

}
