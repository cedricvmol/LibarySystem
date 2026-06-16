package storage;


import domain.Book;

import java.sql.SQLException;
import java.util.List;

public class BookStorage {

    private BookDao bookDao ;


    public BookStorage(BookDao bookDao){
        this.bookDao = bookDao;
    }

    public void saveAll(List<Book> books) throws SQLException {
        bookDao.saveAll(books);
    }

    public List<Book> loadAll() throws SQLException {
        return bookDao.loadAll();
    }
}
