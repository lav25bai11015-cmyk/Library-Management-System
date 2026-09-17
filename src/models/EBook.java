package models;

public class EBook extends Book {

    private double fileSizeMB;
    private String format;

    public EBook(int id, String title, String author, double fileSizeMB, String format) {
        super(id, title, author);
        this.fileSizeMB = fileSizeMB;
        this.format = format;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String getBookType() {
        return "E-Book";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("File Size: " + fileSizeMB + " MB");
        System.out.println("Format: " + format);
    }
}