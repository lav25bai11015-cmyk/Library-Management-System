package models;

public class PhysicalBook extends Book {

    private String shelfLocation;

    public PhysicalBook(int id, String title, String author, String shelfLocation) {
        super(id, title, author);
        this.shelfLocation = shelfLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    @Override
    public String getBookType() {
        return "Physical Book";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Shelf: " + shelfLocation);
    }
}