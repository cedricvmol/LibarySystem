package domain;

import java.time.LocalDate;

public class BookLoan {

    private String loanId;
    private BookCopy copy;
    private Member member;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fee;
    private boolean returned;

    public BookLoan(String loanId, BookCopy copy, Member member, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.copy = copy;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
