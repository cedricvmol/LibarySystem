package storage;

import domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public void saveAll(List<Book> books) throws SQLException {
        connection.setAutoCommit(false);
        try {
            try (Statement stmt = connection.createStatement();
                 PreparedStatement bookStmt = connection.prepareStatement("INSERT OR REPLACE INTO books (" +
                         "isbn,title,genre,author,language,publisher,loanPeriodDays) VALUES (" +
                         "?,?,?,?,?,?,?)");) {
                stmt.execute("DELETE FROM books");
                for (Book book : books) {
                    bookStmt.setString(1, book.getIsbn());
                    bookStmt.setString(2, book.getTitle());
                    bookStmt.setString(3, book.getGenre());
                    bookStmt.setString(4, book.getAuthor());
                    bookStmt.setString(5, book.getLanguage());
                    bookStmt.setString(6, book.getPublisher());
                    bookStmt.setInt(7, book.getLoanPeriodDays());
                    bookStmt.executeUpdate();
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

    public List<Book> loadAll() throws SQLException {

        List<Book> books = new ArrayList<Book>();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM books");
             ResultSet rs = stmt.executeQuery();) {

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String author = rs.getString("author");
                String language = rs.getString("language");
                String publisher = rs.getString("publisher");
                int loanPeriodDays = rs.getInt("loanPeriodDays");

                Book book = new Book(isbn, title, genre, author, language, publisher, loanPeriodDays);
                books.add(book);
            }
        }
        return books;
    }
}
