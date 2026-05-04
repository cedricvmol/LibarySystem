package service;


import domain.BookCopy;
import domain.CopyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceTest {


    private BookService bookService;


    @BeforeEach
    void setUp(){
        bookService = new BookService();
    }

    @Test
    void testAddBook(){
        bookService.addBook("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        assertEquals(1,bookService.getAllBooks().size());
    }

    @Test
    void testAddBookDuplicateIsbn(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner",14);

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook("9780743273565", "The Handmaid's Tale", "Dystopian", "Margaret Atwood", "English", "Anchor Books",15);
        });
    }

    @Test
    void testAddBookDefaultLoanPeriod(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        assertEquals(14,bookService.getBook("9780743273565").getLoanPeriodDays());
    }

    @Test
    void testGetBook(){
        bookService.addBook("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        assertEquals("The Fellowship of the Ring",bookService.getBook("9780261103573").getTitle());
    }

    @Test
    void testGetBookWithUnknowIsbn(){
        bookService.addBook("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        assertThrows(IllegalArgumentException.class , () -> {
            bookService.getBook("9780261103500");
        });
    }

    @Test
    void testGetAllBooksCorrectSize(){
        bookService.addBook("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins");
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook("9780385333481", "The Handmaid's Tale", "Dystopian", "Margaret Atwood", "English", "Anchor Books");
        bookService.addBook("9780679720201", "Crime and Punishment", "Classic", "Fyodor Dostoevsky", "English", "Vintage Books",15);
        assertEquals(4,bookService.getAllBooks().size());
    }

    @Test
    void testAddCopy(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addCopies("9780743273565",15);
        assertEquals(15,bookService.getAllCopiesForBook("9780743273565").size());
    }

    @Test
    void testAddCopyForNonExistingBook(){
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addCopies("9780743273565",15);
        });
    }

    @Test
    void getAllCopiesForBookForNonExistingIsbn(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getAllCopiesForBook("9780743273500");
        });

    }

    @Test
    void getAllCopiesForBookWithNoCopies(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getAllCopiesForBook("9780743273565");
        });
    }

    @Test
    void removeBook(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addCopies("9780743273565",10);
        bookService.removeBook("9780743273565");

        assertEquals(0,bookService.getAllBooks().size());
        assertThrows(IllegalArgumentException.class , () -> {
            bookService.getAllCopiesForBook("9780743273565");
        });
    }

    @Test
    void removeBookWithUnknowIsbn(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");

        assertThrows(IllegalArgumentException.class,()->{
            bookService.removeBook("9780743273500");
        });
    }

    @Test
    void removeBookWithBorrowedState(){
        bookService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addCopies("9780743273565",2);
        ArrayList<BookCopy> copies = (ArrayList<BookCopy>) bookService.getAllCopiesForBook("9780743273565");
        copies.getFirst().setStatus(CopyStatus.BORROWED);

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.removeBook("9780743273565");
        });
    }


}
