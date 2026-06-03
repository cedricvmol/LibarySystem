package service;

import domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LoanService {

    private List<BookLoan> loans = new ArrayList<>();
    private BookService bookService;

    public LoanService(BookService bookService) {
        this.bookService = bookService;
    }

    public void borrowBook(Member member, String isbn) {

        Collection<BookCopy> copies = this.bookService.getAllCopiesForBook(isbn);

        BookCopy copy = copies.stream().filter(bookCopy -> bookCopy.getStatus().equals(CopyStatus.AVAILABLE)).findFirst().orElseThrow(()-> new IllegalArgumentException("No available copy."));
        copy.setStatus(CopyStatus.BORROWED);
        BookLoan loan = new BookLoan(loanIdGenerator(),copy,member,LocalDate.now(),LocalDate.now().plusDays(copy.getBook().getLoanPeriodDays()));
        loans.add(loan);
    }

    public List<BookLoan> getActiveLoansForMember(String memberId){
        return loans.stream().filter(loan -> !loan.isReturned() && loan.getMember().getMemberId().equals(memberId)).collect(Collectors.toList());
    }

    public List<BookLoan> getAllActiveLoans(){
        return loans.stream().filter(loan -> !loan.isReturned()).collect(Collectors.toList());
    }

    public String returnBook(String loanId){
        BookLoan loan = loans.stream().filter(bookLoan -> bookLoan.getLoanId().equals(loanId)).findFirst().orElseThrow(() -> new IllegalArgumentException("No loan was found with that ID."));
        if (loan.isReturned()){
            throw new IllegalArgumentException("The book is already returned.");
        }
        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());
        loan.getCopy().setStatus(CopyStatus.AVAILABLE);

        return loan.getCopy().getBook().getIsbn();
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
