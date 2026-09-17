import models.*;
import services.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        BookService bookService = new BookService();
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();

        initializeData(bookService, userService);

        System.out.println("======================================");
        System.out.println("     LIBRARY MANAGEMENT SYSTEM");
        System.out.println("======================================");

        while (true) {

            System.out.println("\n1. Login");
            System.out.println("2. View All Books");
            System.out.println("3. Search Books");
            System.out.println("4. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    login(
                            bookService,
                            userService,
                            transactionService
                    );
                    break;

                case 2:
                    bookService.displayAllBooks();
                    break;

                case 3:
                    searchBooks(bookService);
                    break;

                case 4:
                    System.out.println(
                            "Thank you for using the Library Management System."
                    );
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeData(
            BookService bookService,
            UserService userService) {

        userService.seedDefaultUsers();

        bookService.loadBooks();

        if (bookService.getAllBooks().isEmpty()) {

            bookService.addBook(
                    new PhysicalBook(
                            101,
                            "Java Programming",
                            "James Gosling",
                            "A-101"
                    )
            );

            bookService.addBook(
                    new PhysicalBook(
                            102,
                            "Clean Code",
                            "Robert C. Martin",
                            "A-102"
                    )
            );

            bookService.addBook(
                    new EBook(
                            103,
                            "Effective Java",
                            "Joshua Bloch",
                            5.2,
                            "PDF"
                    )
            );

            bookService.addBook(
                    new EBook(
                            104,
                            "Data Structures",
                            "Mark Allen",
                            8.5,
                            "EPUB"
                    )
            );
        }
    }

    private static void login(
            BookService bookService,
            UserService userService,
            TransactionService transactionService) {

        scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(email, password);

        if (user == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println("\nLogin successful.");
        user.displayProfile();

        if (user instanceof Admin) {

            adminMenu(
                    bookService,
                    userService,
                    transactionService
            );

        } else if (user instanceof Member) {

            memberMenu(
                    (Member) user,
                    bookService,
                    transactionService
            );
        }
    }

    private static void adminMenu(
            BookService bookService,
            UserService userService,
            TransactionService transactionService) {

        while (true) {

            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. View Books");
            System.out.println("2. Add Book");
            System.out.println("3. Remove Book");
            System.out.println("4. View Users");
            System.out.println("5. View Transaction History");
            System.out.println("6. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    bookService.displayAllBooks();
                    break;

                case 2:
                    addBook(bookService);
                    break;

                case 3:
                    int id = readInt("Enter book ID: ");
                    bookService.removeBook(id);
                    break;

                case 4:
                    userService.displayAllUsers();
                    break;

                case 5:
                    transactionService.displayTransactionHistory();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void memberMenu(
            Member member,
            BookService bookService,
            TransactionService transactionService) {

        while (true) {

            System.out.println("\n========== MEMBER MENU ==========");
            System.out.println("1. View Books");
            System.out.println("2. Search Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View My Profile");
            System.out.println("6. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    bookService.displayAllBooks();
                    break;

                case 2:
                    searchBooks(bookService);
                    break;

                case 3:
                    borrowBook(
                            member,
                            bookService,
                            transactionService
                    );
                    break;

                case 4:
                    returnBook(
                            member,
                            bookService,
                            transactionService
                    );
                    break;

                case 5:
                    member.displayProfile();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addBook(BookService bookService) {

        scanner.nextLine();

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        int id = readInt("Enter book ID: ");

        System.out.println("1. Physical Book");
        System.out.println("2. E-Book");

        int type = readInt("Choose type: ");

        if (type == 1) {

            scanner.nextLine();

            System.out.print("Enter shelf location: ");
            String shelf = scanner.nextLine();

            bookService.addBook(
                    new PhysicalBook(
                            id,
                            title,
                            author,
                            shelf
                    )
            );

        } else if (type == 2) {

            scanner.nextLine();

            System.out.print("Enter format: ");
            String format = scanner.nextLine();

            System.out.print("Enter file size in MB: ");
            double size = scanner.nextDouble();

            bookService.addBook(
                    new EBook(
                            id,
                            title,
                            author,
                            size,
                            format
                    )
            );

        } else {

            System.out.println("Invalid book type.");
        }
    }

    private static void searchBooks(BookService bookService) {

        scanner.nextLine();

        System.out.print("Enter title or author keyword: ");
        String keyword = scanner.nextLine();

        List<Book> results = bookService.searchBooks(keyword);

        if (results.isEmpty()) {

            System.out.println("No matching books found.");
            return;
        }

        System.out.println("\nSearch Results:");

        for (Book book : results) {

            book.displayDetails();

            System.out.println("--------------------");
        }
    }

    private static void borrowBook(
            Member member,
            BookService bookService,
            TransactionService transactionService) {

        int bookId = readInt("Enter book ID to borrow: ");

        Book book = bookService.findBookById(bookId);

        transactionService.borrowBook(
                member,
                book
        );
    }

    private static void returnBook(
            Member member,
            BookService bookService,
            TransactionService transactionService) {

        int bookId = readInt("Enter book ID to return: ");

        Book book = bookService.findBookById(bookId);

        transactionService.returnBook(
                member,
                book
        );
    }

    private static int readInt(String message) {

        System.out.print(message);

        while (!scanner.hasNextInt()) {

            System.out.println("Please enter a valid number.");

            scanner.next();

            System.out.print(message);
        }

        return scanner.nextInt();
    }
}