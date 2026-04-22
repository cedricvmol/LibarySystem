package domain;

import java.time.LocalDateTime;

public class BookLoan {

    private String loanId;
    private BookCopy copy;
    private Member member;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private double fee;
    private boolean returned;

    public BookLoan(String loanId,
                    BookCopy copy,
                    Member member,
                    LocalDateTime loanDate,
                    LocalDateTime dueDate,
                    LocalDateTime returnDate,
                    double fee,
                    boolean returned) {

        this.loanId = loanId;
        this.copy = copy;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fee = fee;


    }

    public BookCopy getCopy() {
        return copy;
    }

    public boolean isReturned() {
        return returned;
    }

    public double getFee() {
        return fee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
