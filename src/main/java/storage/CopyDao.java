package storage;

import domain.Book;
import domain.BookCopy;
import domain.CopyStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CopyDao {

    private Connection connection;


    public CopyDao(Connection connection) {
        this.connection = connection;
    }

    public void saveAll(List<BookCopy> copies) throws SQLException {
        connection.setAutoCommit(false);
        try {
            try (Statement stmt = connection.createStatement();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO copies (" +
                         "copyId,status,bookIsbn) VALUES (?,?,?)")) {
                stmt.execute("DELETE FROM copies");

                for (BookCopy copy : copies) {
                    preparedStatement.setString(1, copy.getCopyId());
                    preparedStatement.setString(2, copy.getStatus().toString());
                    preparedStatement.setString(3, copy.getBook().getIsbn());
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            }

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<BookCopy> loadAll(Map<String, Book> books) throws SQLException {
        List<BookCopy> copies = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM copies");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String copyId = rs.getString("copyId");
                CopyStatus status = CopyStatus.valueOf(rs.getString("status"));
                String bookIsbn = rs.getString("bookIsbn");
                Book book = books.get(bookIsbn);
                BookCopy copy = new BookCopy(copyId, book, status);

                copies.add(copy);
            }
        }
        return copies;
    }
}
