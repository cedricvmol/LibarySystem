package domain;

public class Book {

    private String isbn;
    private String title;
    private String author;
    private String language;
    private String publisher;


    public Book(String isbn, String title, String author, String language, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.language = language;
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public String getPublisher() {
        return publisher;
    }

}
