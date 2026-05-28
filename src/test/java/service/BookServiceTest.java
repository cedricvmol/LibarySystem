package service;


import domain.Book;
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
        Book book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        bookService.addBook(book);
        assertEquals(1,bookService.getAllBooks().size());
    }

    @Test
    void testAddBookDuplicateIsbn(){
        Book book = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner",14);
        bookService.addBook(book);

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook(book);
        });
    }

    @Test
    void testAddBookDefaultLoanPeriod(){
        Book book = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner",14);
        bookService.addBook(book);
        assertEquals(14,bookService.getBook("9780743273565").getLoanPeriodDays());
    }

    @Test
    void testGetBook(){
        Book book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        bookService.addBook(book);
        assertEquals("The Fellowship of the Ring",bookService.getBook("9780261103573").getTitle());
    }

    @Test
    void testGetBookWithUnknowIsbn(){
        Book book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins",14);
        bookService.addBook(book);
        assertThrows(IllegalArgumentException.class , () -> {
            bookService.getBook("9780261103500");
        });
    }

    @Test
    void testGetAllBooksCorrectSize(){
        Book book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins");
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        Book book2 = new Book("9780385333481", "The Handmaid's Tale", "Dystopian", "Margaret Atwood", "English", "Anchor Books");
        Book book3 = new Book("9780679720201", "Crime and Punishment", "Classic", "Fyodor Dostoevsky", "English", "Vintage Books",15);
        bookService.addBook(book);
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        assertEquals(4,bookService.getAllBooks().size());
    }

    @Test
    void testAddCopy(){
        Book book = new Book("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins");
        bookService.addBook(book);
        bookService.addCopies("9780261103573",15);
        assertEquals(15,bookService.getAllCopiesForBook("9780261103573").size());
    }

    @Test
    void testAddCopyForNonExistingBook(){
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addCopies("9780743273565",15);
        });
    }

    @Test
    void getAllCopiesForBookForNonExistingIsbn(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getAllCopiesForBook("9780743273500");
        });

    }

    @Test
    void getAllCopiesForBookWithNoCopies(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getAllCopiesForBook("9780743273565");
        });
    }

    @Test
    void removeBook(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        bookService.addCopies("9780743273565",10);
        bookService.removeBook("9780743273565");

        assertEquals(0,bookService.getAllBooks().size());
        assertThrows(IllegalArgumentException.class , () -> {
            bookService.getAllCopiesForBook("9780743273565");
        });
    }

    @Test
    void removeBookWithUnknowIsbn(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);

        assertThrows(IllegalArgumentException.class,()->{
            bookService.removeBook("9780743273500");
        });
    }

    @Test
    void removeBookWithBorrowedState(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        bookService.addCopies("9780743273565",2);
        ArrayList<BookCopy> copies = (ArrayList<BookCopy>) bookService.getAllCopiesForBook("9780743273565");
        copies.getFirst().setStatus(CopyStatus.BORROWED);

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.removeBook("9780743273565");
        });
    }

    @Test
    void searchBookWithTitle(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        assertEquals(1,bookService.searchBooks("Great", "title").size());
    }

    @Test
    void searchBookWithAuthor(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        assertEquals(1,bookService.searchBooks("Scott", "author").size());
    }

    @Test
    void searchBookWithGenre(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        assertEquals(1,bookService.searchBooks("Classic", "genre").size());
    }

    @Test
    void searchBookWithInvalidField(){
        Book book1 = new Book("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        bookService.addBook(book1);
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.searchBooks("Classic","xxx");
        });

    }


}
