package models;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {

    private String membershipType;
    private List<Integer> borrowedBookIds;

    public Member(int id, String name, String email, String password, String membershipType) {
        super(id, name, email, password);
        this.membershipType = membershipType;
        this.borrowedBookIds = new ArrayList<>();
    }

    public String getMembershipType() {
        return membershipType;
    }

    public List<Integer> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    public int getBorrowLimit() {
        if (membershipType.equalsIgnoreCase("PREMIUM")) {
            return 7;
        }
        return 3;
    }

    public void borrowBook(int bookId) {
        borrowedBookIds.add(bookId);
    }

    public void returnBook(int bookId) {
        borrowedBookIds.remove(Integer.valueOf(bookId));
    }

    @Override
    public String getRole() {
        return "Member";
    }

    @Override
    public void displayProfile() {
        System.out.println("Role: Member");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Membership: " + membershipType);
        System.out.println("Borrowed Books: " + borrowedBookIds.size());
    }
}