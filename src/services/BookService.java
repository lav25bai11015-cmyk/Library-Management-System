package services;

import models.Book;
import models.PhysicalBook;
import models.EBook;
import interfaces.Searchable;
import utils.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookService implements Searchable<Book> {

    private List<Book> books;
    private final String fileName = "data/books.dat";

    public BookService() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {

        if (findBookById(book.getId()) != null) {
            System.out.println("A book with this ID already exists.");
            return;
        }

        books.add(book);
        saveBooks();

        System.out.println("Book added successfully.");
    }

    public boolean removeBook(int id) {

        Book book = findBookById(id);

        if (book != null) {
            books.remove(book);
            saveBooks();

            System.out.println("Book removed successfully.");
            return true;
        }

        System.out.println("Book not found.");
        return false;
    }

    public Book findBookById(int id) {

        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    @Override
    public List<Book> search(String keyword) {

        List<Book> results = new ArrayList<>();

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {

                results.add(book);
            }
        }

        return results;
    }

    public List<Book> searchBooks(String keyword) {
        return search(keyword);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void displayAllBooks() {

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book book : books) {

            book.displayDetails();

            System.out.println("--------------------");
        }
    }

    public void loadBooks() {

        try {

            List<String> data = FileHandler.loadData(fileName);

            for (String line : data) {

                String[] parts = line.split("\\|");

                if (parts.length < 6) {
                    continue;
                }

                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                String type = parts[3];

                Book book;

                if (type.equalsIgnoreCase("PHYSICAL")) {

                    String shelf = parts[4];

                    book = new PhysicalBook(
                            id,
                            title,
                            author,
                            shelf
                    );

                } else {

                    double size = Double.parseDouble(parts[4]);
                    String format = parts[5];

                    book = new EBook(
                            id,
                            title,
                            author,
                            size,
                            format
                    );
                }

                book.setAvailable(
                        Boolean.parseBoolean(parts[parts.length - 1])
                );

                books.add(book);
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Unable to load saved books. Starting with default data."
            );
        }
    }

    private void saveBooks() {

        List<String> data = new ArrayList<>();

        for (Book book : books) {

            if (book instanceof PhysicalBook) {

                PhysicalBook physicalBook =
                        (PhysicalBook) book;

                data.add(
                        book.getId() + "|"
                        + book.getTitle() + "|"
                        + book.getAuthor() + "|PHYSICAL|"
                        + physicalBook.getShelfLocation() + "|"
                        + book.isAvailable()
                );

            } else if (book instanceof EBook) {

                EBook ebook = (EBook) book;

                data.add(
                        book.getId() + "|"
                        + book.getTitle() + "|"
                        + book.getAuthor() + "|EBOOK|"
                        + ebook.getFileSizeMB() + "|"
                        + ebook.getFormat() + "|"
                        + book.isAvailable()
                );
            }
        }

        try {

            FileHandler.saveData(fileName, data);

        } catch (IOException e) {

            System.out.println(
                    "Warning: Unable to save book data."
            );
        }
    }
}