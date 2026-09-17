package services;

import models.User;
import models.Admin;
import models.Member;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        if (findUserByEmail(user.getEmail()) != null) {
            System.out.println("A user with this email already exists.");
            return;
        }

        users.add(user);
        System.out.println("User added successfully.");
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }

        return null;
    }

    public User login(String email, String password) {
        User user = findUserByEmail(email);

        if (user != null && user.checkPassword(password)) {
            return user;
        }

        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }

        for (User user : users) {
            user.displayProfile();
            System.out.println("--------------------");
        }
    }

    public void seedDefaultUsers() {
        if (users.isEmpty()) {
            addUser(new Admin(
                    1,
                    "Library Admin",
                    "admin@library.com",
                    "admin123"
            ));

            addUser(new Member(
                    2,
                    "John Member",
                    "john@library.com",
                    "john123",
                    "REGULAR"
            ));

            addUser(new Member(
                    3,
                    "Premium Member",
                    "premium@library.com",
                    "premium123",
                    "PREMIUM"
            ));
        }
    }
}