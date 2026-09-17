package services;

import models.Book;
import models.Member;
import models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private List<Transaction> transactions;
    private int nextTransactionId;

    public TransactionService() {
        transactions = new ArrayList<>();
        nextTransactionId = 1;
    }

    public boolean borrowBook(Member member, Book book) {

        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed.");
            return false;
        }

        if (member.getBorrowedBookIds().size() >= member.getBorrowLimit()) {
            System.out.println("Borrowing limit reached.");
            return false;
        }

        Transaction transaction = new Transaction(
                nextTransactionId++,
                member.getId(),
                book.getId()
        );

        transactions.add(transaction);
        member.borrowBook(book.getId());
        book.setAvailable(false);

        System.out.println("Book borrowed successfully.");
        return true;
    }

    public boolean returnBook(Member member, Book book) {

        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        for (Transaction transaction : transactions) {

            if (transaction.getMemberId() == member.getId()
                    && transaction.getBookId() == book.getId()
                    && !transaction.isReturned()) {

                transaction.markReturned();
                member.returnBook(book.getId());
                book.setAvailable(true);

                System.out.println("Book returned successfully.");
                System.out.println("Fine: ₹" + transaction.calculateFine());

                return true;
            }
        }

        System.out.println("No active borrowing transaction found.");
        return false;
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public void displayTransactionHistory() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(
                    "Transaction ID: " + transaction.getTransactionId()
                    + " | Member ID: " + transaction.getMemberId()
                    + " | Book ID: " + transaction.getBookId()
                    + " | Borrowed: " + transaction.getBorrowDate()
                    + " | Returned: " + transaction.isReturned()
            );

            if (transaction.isReturned()) {
                System.out.println("Return Date: " + transaction.getReturnDate());
                System.out.println("Fine: ₹" + transaction.calculateFine());
            }

            System.out.println("--------------------");
        }
    }
}