package service;

import domain.BookCopy;
import domain.BookLoan;
import domain.CopyStatus;
import domain.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class LoanService {

    private List<BookLoan> loans = new ArrayList<>();
    private BookService bookService;

    public LoanService(BookService bookService) {
        this.bookService = bookService;
    }

    public void borrowBook(Member member, String isbn) {

        Collection<BookCopy> copies = this.bookService.getAllCopiesForBook(isbn);

        for (BookCopy copy : copies) {
            if (copy.getStatus().equals(CopyStatus.AVAILABLE)) {
                BookLoan bookLoan = new BookLoan(loanIdGenerator(), copy, member, LocalDate.now(), LocalDate.now().plusDays(copy.getBook().getLoanPeriodDays()));
                copy.setStatus(CopyStatus.BORROWED);
                loans.add(bookLoan);
                return;
            }
        }
        throw new IllegalArgumentException("There is no copy available to borrow for this book.");
    }

    public String loanIdGenerator() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        id.append("L");
        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            id.append(n);
        }
        return id.toString();
    }
}
