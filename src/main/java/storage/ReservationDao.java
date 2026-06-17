package storage;


import domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationDao {

    private Connection connection;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReservationDao(Connection connection){
        this.connection = connection;
    }

    public void saveAll(List<Reservation> reservationList) throws SQLException{
        connection.setAutoCommit(false);
        try {
            try (Statement stmt = connection.createStatement();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO reservations (" +
                         "reservationId,memberId,bookIsbn,reservationDate,reservationStatus) VALUES (?,?,?,?,?)"))
            {
                stmt.execute("DELETE FROM reservations");

                for (Reservation reservation : reservationList){
                    preparedStatement.setString(1,reservation.getReservationId());
                    preparedStatement.setString(2,reservation.getMember().getMemberId());
                    preparedStatement.setString(3,reservation.getBook().getIsbn());
                    preparedStatement.setString(4,reservation.getReservationDate().format(formatter));
                    preparedStatement.setString(5,reservation.getReservationStatus().toString());
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            }
        } catch (SQLException e){
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Reservation> loadAll(Map<String, Book> books, Map<String, Member> members) throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservations");
            ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                String reservationId = rs.getString("reservationId");
                String memberId = rs.getString("memberId");
                String bookIsbn = rs.getString("bookIsbn");
                String reservationDate = rs.getString("reservationDate");
                String reservationStatus = rs.getString("reservationStatus");

                Book book = books.get(bookIsbn);
                Member member = members.get(memberId);

                LocalDate reservationDateCon = LocalDate.parse(reservationDate,formatter);

                Reservation reservation = new Reservation(reservationId,member,book,reservationDateCon);
                reservation.setReservationStatus(ReservationStatus.valueOf(reservationStatus));

                reservationList.add(reservation);
            }
        }
        return  reservationList;
    }
}
