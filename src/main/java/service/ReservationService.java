package service;

import domain.*;
import storage.ReservationStorage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private BookService bookService;
    private ReservationStorage reservationStorage;


    public ReservationService(BookService bookService,ReservationStorage reservationStorage){
        this.bookService = bookService;
        this.reservationStorage = reservationStorage;
    }


    public void createReservation(Member member, String isbn){
        if(bookService.getAllCopiesForBook(isbn).stream().anyMatch(bookCopy -> bookCopy.getStatus().equals(CopyStatus.AVAILABLE))){
            throw new IllegalArgumentException("A copy is available — borrow it instead.");
        }

        Book bookToReservation = bookService.getBook(isbn);
        Reservation reservation = new Reservation(reservationIdGenerator(),member, bookToReservation, LocalDate.now());
        reservations.add(reservation);
        saveReservations();
    }

    public void cancelReservation(String reservationId){
        Reservation reservationToCancel = reservations.stream().filter(reservation -> reservation.getReservationId().equals(reservationId)).findFirst().orElseThrow(() -> new IllegalArgumentException("No reservation found with that ID."));
        reservationToCancel.setReservationStatus(ReservationStatus.CANCELLED);
        saveReservations();
    }

    public List<Reservation> getPendingReservationsForMember(String memberId){
        return reservations.stream().filter(reservation -> reservation.getMember().getMemberId().equals(memberId) && reservation.getReservationStatus().equals(ReservationStatus.PENDING)).sorted(Comparator.comparing(Reservation::getReservationDate)).collect(Collectors.toList());
    }

    public List<Reservation> getAllPendingReservations(){
        return reservations.stream().filter(reservation -> reservation.getReservationStatus().equals(ReservationStatus.PENDING)).sorted(Comparator.comparing(Reservation::getReservationDate)).collect(Collectors.toList());
    }

    public void fulfillNextReservation(String isbn){
        reservations.stream().filter(reservation -> reservation.getBook().getIsbn().equals(isbn) && reservation.getReservationStatus().equals(ReservationStatus.PENDING))
                .sorted(Comparator.comparing(Reservation::getReservationDate))
                .findFirst()
                .ifPresent(reservation -> reservation.setReservationStatus(ReservationStatus.FULFILLED));
        saveReservations();
    }


    public String reservationIdGenerator() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        id.append("R");
        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            id.append(n);
        }
        return id.toString();
    }

    void load(Map<String, Book> books, Map<String, Member> members) throws SQLException {
        reservations = new ArrayList<>(reservationStorage.loadAll(books, members));
    }

    void saveReservations() {
        if (reservationStorage == null) {
            return;
        }
        try {
            reservationStorage.saveAll(reservations);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save reservations.", e);
        }
    }

}
