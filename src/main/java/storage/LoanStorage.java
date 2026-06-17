package storage;

import domain.BookCopy;
import domain.BookLoan;
import domain.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LoanStorage {

    private LoanDao loanDao;

    public LoanStorage(LoanDao loanDao){
        this.loanDao = loanDao;
    }

    public void saveAll(List<BookLoan> loans) throws SQLException {
        loanDao.saveAll(loans);
    }

    public List<BookLoan> loadAll(Map<String, BookCopy> copies, Map<String, Member> members) throws SQLException{
        return loanDao.loadAll(copies,members);
    }
}
