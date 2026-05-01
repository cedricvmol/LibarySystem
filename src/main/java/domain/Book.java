package domain;

public class Book {

    private String isbn;
    private String title;
    private String genre;
    private String author;
    private String language;
    private String publisher;
    private int loanPeriodDays;


    public Book(String isbn, String title, String genre, String author, String language, String publisher,int loanPeriodDays) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.language = language;
        this.publisher = publisher;
        this.loanPeriodDays = loanPeriodDays;
    }

    public Book(String isbn, String title, String genre, String author, String language, String publisher) {
        this(isbn,title,genre,author,language,publisher,14);

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

    public int getLoanPeriodDays(){
        return loanPeriodDays;
    }
}
