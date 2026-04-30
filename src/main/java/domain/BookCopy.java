package domain;

public class BookCopy {

    private String copyId;
    private Book book;
    private CopyStatus status;

    public BookCopy(String copyId, Book book, CopyStatus status) {
        this.book = book;
        this.copyId = copyId;
        this.status = status;
    }

    public void setStatus(CopyStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public String getCopyId() {
        return copyId;
    }

    public CopyStatus getStatus() {
        return status;
    }
}
