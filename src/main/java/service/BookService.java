package service;

import domain.Book;
import domain.BookCopy;
import domain.CopyStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class BookService {

    private HashMap<String, Book> books;
    private HashMap<String, BookCopy> bookCopies;


    public BookService() {
        this.books = new HashMap<>();
        this.bookCopies = new HashMap<>();
    }

    public void addBook(String isbn, String title, String genre, String author, String language, String publisher,int loanPeriodDays) {
        if (books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is already registered.");
        }
        books.put(isbn, new Book(isbn, title, genre, author, language, publisher,loanPeriodDays));
    }

    public void addBook(String isbn, String title, String genre, String author, String language, String publisher) {
        if (books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is already registered.");
        }
        books.put(isbn, new Book(isbn, title, genre, author, language, publisher,14));
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

    public Book getBook(String isbn) {
        if (!books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is not yet registered in the library.");
        }
        return books.get(isbn);
    }


    public Collection<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }


    public void removeBook(String isbn){
        if (!books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is not yet registered in the library.");
        }

        ArrayList<String> copiesIdToRemove = new ArrayList<>();
        for (BookCopy copy : bookCopies.values()){
            if(copy.getBook().getIsbn().equals(isbn)){
                if(copy.getStatus().equals(CopyStatus.BORROWED)){
                    throw new IllegalArgumentException("We cannot remove this book. They are still copies that have an active loan.");
                }
                copiesIdToRemove.add(copy.getCopyId());
            }
        }
        for (String ids : copiesIdToRemove){
            bookCopies.remove(ids);
        }

        books.remove(isbn);

    }

    public Collection<BookCopy> getAllCopiesForBook(String isbn) {
        ArrayList<BookCopy> copies = new ArrayList<>();

        if (!books.containsKey(isbn)) {
            throw new IllegalArgumentException("Book with ISBN: " + isbn + " is not yet registered in the library.");
        }
        for (BookCopy copy : bookCopies.values()) {
            if (copy.getBook().getIsbn().equals(isbn)) {
                copies.add(copy);
            }
        }
        if (copies.isEmpty()) {
            throw new IllegalArgumentException("There are no book copies for the following isbn code: " + isbn);
        }
        return copies;
    }

    public String copyIdGenerator(String title) {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        id.append(title);

        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            id.append(n);
        }

        return id.toString();
    }


}
