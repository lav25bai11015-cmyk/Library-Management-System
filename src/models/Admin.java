package models;

public class Admin extends User {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void displayProfile() {
        System.out.println("Role: Admin");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
    }
}