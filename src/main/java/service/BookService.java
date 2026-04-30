package service;

import domain.Book;
import domain.BookCopy;
import domain.CopyStatus;

import java.util.HashMap;
import java.util.Random;

public class BookService {

    private HashMap<String, Book> books;
    private HashMap<String, BookCopy> bookCopies;


    public BookService() {
        this.books = new HashMap<>();
        this.bookCopies = new HashMap<>();
    }

    public void addBook(String isbn, String title, String genre, String author, String language, String publisher) {
        if (books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is already registered.");
        }
        books.put(isbn, new Book(isbn, title, genre, author, language, publisher));
    }

    public void addCopies(String isbn, int amount) {
        if (!books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is not yet registered in the library.");
        }

        for (int i = 0; i < amount; i++) {
            String copyId = copyIdGenerator(books.get(isbn).getTitle());
            bookCopies.put(copyId, new BookCopy(copyId, books.get(isbn), CopyStatus.AVAILABLE));
        }

    }

    public String copyIdGenerator(String title) {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            id.append(n);
        }

        return id.toString();
    }


}
