package storage;

import domain.Book;
import domain.BookCopy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CopyStorage {

    private CopyDao copyDao;

    public CopyStorage(CopyDao copyDao){
        this.copyDao = copyDao;
    }

    public void saveAll(List<BookCopy> copies) throws SQLException {
        copyDao.saveAll(copies);
    }

    public List<BookCopy> loadAll(Map<String, Book> books) throws SQLException{
        return copyDao.loadAll(books);
    }
}
