package service;

import domain.Book;

import java.util.HashMap;

public class BookService {

    private HashMap<String, Book> books;

    public BookService(){
        this.books = new HashMap<>();
    }
    public void addBook(String isbn,String title,String genre,String author,String language,String publisher){
        if(books.containsKey(isbn)){
            throw new IllegalArgumentException("Book with ISBN " + isbn + " is already registered.");
        }
        books.put(isbn,new Book(isbn,title,genre,author,language,publisher));
    }
}
