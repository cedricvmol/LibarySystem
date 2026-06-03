package domain;

import java.time.LocalDate;

public class Reservation {
    private String reservationId;
    private Member member;
    private Book book;
    private LocalDate reservationDate;
    private ReservationStatus reservationStatus;


    public Reservation(String reservationId,Member member,Book book,LocalDate reservationDate){
        this.reservationId = reservationId;
        this.member = member;
        this.book = book;
        this.reservationDate = reservationDate;
        this.reservationStatus = ReservationStatus.PENDING;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus){
        this.reservationStatus = reservationStatus;
    }
}
