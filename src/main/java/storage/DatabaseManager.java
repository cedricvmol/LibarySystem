package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final String CONNECTION_URL = "jdbc:sqlite:library.db";
    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void initTables() throws SQLException {

        try (Statement stmt = this.connection.createStatement();){
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "isbn TEXT PRIMARY KEY," +
                    "title TEXT NOT NULL," +
                    "genre TEXT NOT NULL," +
                    "author TEXT NOT NULL," +
                    "language TEXT NOT NULL," +
                    "publisher TEXT NOT NULL," +
                    "loanPeriodDays INTEGER NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS members (" +
                    "memberId TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "address TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "password TEXT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS copies (" +
                    "copyId TEXT PRIMARY KEY," +
                    "status TEXT NOT NULL," +
                    "bookIsbn TEXT NOT NULL," +
                    "FOREIGN KEY (bookIsbn) REFERENCES books(isbn))");

            stmt.execute("CREATE TABLE IF NOT EXISTS loans (" +
                    "loanId TEXT PRIMARY KEY," +
                    "copyId TEXT NOT NULL," +
                    "memberId TEXT NOT NULL," +
                    "loanDate TEXT NOT NULL," +
                    "dueDate TEXT NOT NULL," +
                    "returnDate TEXT," +
                    "fee REAL NOT NULL," +
                    "returned INTEGER NOT NULL," +
                    "FOREIGN KEY (copyId) REFERENCES copies(copyId)," +
                    "FOREIGN KEY (memberId) REFERENCES members(memberId))");

            stmt.execute("CREATE TABLE IF NOT EXISTS reservations (" +
                    "reservationId TEXT PRIMARY KEY," +
                    "memberId TEXT NOT NULL," +
                    "bookIsbn TEXT NOT NULL," +
                    "reservationDate TEXT NOT NULL," +
                    "reservationStatus TEXT NOT NULL," +
                    "FOREIGN KEY (memberId) REFERENCES members(memberId)," +
                    "FOREIGN KEY (bookIsbn) REFERENCES books(isbn))");
        }
    }
}
