package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final String CONNECTION_URL = "jdbc:sqlite:data/bank.db";

    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        return conn;
    }

    public void initTables() throws  SQLException {

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();) {

        }
    }
}
