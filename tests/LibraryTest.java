import models.*;
import services.*;

public class LibraryTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       LIBRARY SYSTEM TESTS");
        System.out.println("======================================");

        testAddAndFindBook();
        testDuplicateBook();
        testSearchBook();
        testBorrowBook();
        testReturnBook();
        testBorrowLimit();

        System.out.println("\n======================================");
        System.out.println("Tests Passed: " + passed);
        System.out.println("Tests Failed: " + failed);
        System.out.println("======================================");

        if (failed == 0) {
            System.out.println("All tests passed successfully.");
        } else {
            System.out.println("Some tests failed.");
        }
    }

    private static void testAddAndFindBook() {

        BookService service = new BookService();

        Book book = new PhysicalBook(
                1,
                "Test Java",
                "Test Author",
                "T-101"
        );

        service.addBook(book);

        if (service.findBookById(1) != null) {
            pass("Add and find book");
        } else {
            fail("Add and find book");
        }
    }

    private static void testDuplicateBook() {

        BookService service = new BookService();

        Book firstBook = new PhysicalBook(
                1,
                "Book One",
                "Author One",
                "A-101"
        );

        Book secondBook = new PhysicalBook(
                1,
                "Book Two",
                "Author Two",
                "A-102"
        );

        service.addBook(firstBook);
        service.addBook(secondBook);

        if (service.getAllBooks().size() == 1) {
            pass("Duplicate book ID prevention");
        } else {
            fail("Duplicate book ID prevention");
        }
    }

    private static void testSearchBook() {

        BookService service = new BookService();

        service.addBook(
                new PhysicalBook(
                        1,
                        "Java Programming",
                        "James Gosling",
                        "A-101"
                )
        );

        if (service.search("java").size() == 1) {
            pass("Book search");
        } else {
            fail("Book search");
        }
    }

    private static void testBorrowBook() {

        BookService bookService = new BookService();
        TransactionService transactionService =
                new TransactionService();

        Member member = new Member(
                1,
                "Test Member",
                "test@example.com",
                "password",
                "REGULAR"
        );

        Book book = new PhysicalBook(
                1,
                "Java",
                "Author",
                "A-101"
        );

        bookService.addBook(book);

        boolean result =
                transactionService.borrowBook(member, book);

        if (result
                && !book.isAvailable()
                && member.getBorrowedBookIds().contains(1)) {

            pass("Borrow book");

        } else {
            fail("Borrow book");
        }
    }

    private static void testReturnBook() {

        BookService bookService = new BookService();
        TransactionService transactionService =
                new TransactionService();

        Member member = new Member(
                1,
                "Test Member",
                "test@example.com",
                "password",
                "REGULAR"
        );

        Book book = new PhysicalBook(
                1,
                "Java",
                "Author",
                "A-101"
        );

        bookService.addBook(book);

        transactionService.borrowBook(member, book);

        boolean result =
                transactionService.returnBook(member, book);

        if (result
                && book.isAvailable()
                && !member.getBorrowedBookIds().contains(1)) {

            pass("Return book");

        } else {
            fail("Return book");
        }
    }

    private static void testBorrowLimit() {

        TransactionService transactionService =
                new TransactionService();

        Member member = new Member(
                1,
                "Regular Member",
                "regular@example.com",
                "password",
                "REGULAR"
        );

        boolean allBorrowed = true;

        for (int i = 1; i <= 4; i++) {

            Book book = new PhysicalBook(
                    i,
                    "Book " + i,
                    "Author",
                    "A-" + i
            );

            boolean result =
                    transactionService.borrowBook(
                            member,
                            book
                    );

            if (i <= 3 && !result) {
                allBorrowed = false;
            }

            if (i == 4 && result) {
                allBorrowed = false;
            }
        }

        if (allBorrowed) {
            pass("Regular member borrowing limit");
        } else {
            fail("Regular member borrowing limit");
        }
    }

    private static void pass(String testName) {

        passed++;

        System.out.println("[PASS] " + testName);
    }

    private static void fail(String testName) {

        failed++;

        System.out.println("[FAIL] " + testName);
    }
}