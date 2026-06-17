package storage;

import domain.BookCopy;
import domain.BookLoan;
import domain.Member;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoanDao {

    private Connection connection;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LoanDao(Connection connection) {
        this.connection = connection;
    }

    public void saveAll(List<BookLoan> loans) throws SQLException {


        connection.setAutoCommit(false);

        try {

            try (Statement stmt = connection.createStatement();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO loans (" +
                         "loanId,copyId,memberId,loanDate,dueDate,returnDate,fee,returned) VALUES  (?,?,?,?,?,?,?,?)")) {

                stmt.execute("DELETE FROM loans");

                for (BookLoan loan : loans) {
                    preparedStatement.setString(1, loan.getLoanId());
                    preparedStatement.setString(2, loan.getCopy().getCopyId());
                    preparedStatement.setString(3, loan.getMember().getMemberId());
                    preparedStatement.setString(4, loan.getLoanDate().format(formatter));
                    preparedStatement.setString(5, loan.getDueDate().format(formatter));
                    if (loan.getReturnDate() != null) {
                        preparedStatement.setString(6, loan.getReturnDate().format(formatter));
                    } else {
                        preparedStatement.setNull(6, Types.VARCHAR);
                    }
                    preparedStatement.setDouble(7, loan.getFee());
                    preparedStatement.setBoolean(8, loan.isReturned());
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

    public List<BookLoan> loadAll(Map<String,BookCopy> copies, Map<String,Member> members) throws SQLException{
        List<BookLoan> loans = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM loans");
            ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                String loanId = rs.getString("loanId");
                String copyId = rs.getString("copyId");
                String memberId = rs.getString("memberId");
                String loanDate = rs.getString("loanDate");
                String dueDate = rs.getString("dueDate");
                String returnDate = rs.getString("returnDate");
                Double fee = rs.getDouble("fee");
                boolean isReturned = rs.getBoolean("returned");

                LocalDate loanDateCon = LocalDate.parse(loanDate, formatter);
                LocalDate dueDateCon = LocalDate.parse(dueDate,formatter);
                LocalDate returnDateCon;
                if(returnDate != null){
                    returnDateCon = LocalDate.parse(returnDate ,formatter);
                } else {
                    returnDateCon = null;
                }

                BookCopy copy = copies.get(copyId);
                Member member = members.get(memberId);
                BookLoan bookLoan = new BookLoan(loanId,copy,member,loanDateCon,dueDateCon);

                bookLoan.setFee(fee);
                bookLoan.setReturned(isReturned);
                bookLoan.setReturnDate(returnDateCon);

                loans.add(bookLoan);

            }
        }
        return loans;
    }
}
