package models;

import java.time.LocalDate;

public class Transaction {

    private int transactionId;
    private int memberId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public Transaction(int transactionId, int memberId, int bookId) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
        this.returned = false;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.returned = true;
    }

    public long calculateFine() {
        if (!returned) {
            return 0;
        }

        long daysBorrowed = java.time.temporal.ChronoUnit.DAYS.between(
                borrowDate, returnDate
        );

        if (daysBorrowed > 14) {
            return (daysBorrowed - 14) * 2;
        }

        return 0;
    }
}