package domain;

public class Book {

    private String isbn;
    private String title;
    private String genre;
    private String author;
    private String language;
    private String publisher;


    public Book(String isbn, String title, String genre, String author, String language, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }
}
