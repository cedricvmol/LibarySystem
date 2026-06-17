package storage;

import domain.Book;
import domain.Member;
import domain.Reservation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReservationStorage {

    private ReservationDao reservationDao;


    public ReservationStorage(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }

    public void saveAll(List<Reservation> reservations) throws SQLException {
        reservationDao.saveAll(reservations);
    }

    public List<Reservation> loadAll(Map<String, Book> books, Map<String, Member> members) throws SQLException{
        return reservationDao.loadAll(books,members);
    }
}
