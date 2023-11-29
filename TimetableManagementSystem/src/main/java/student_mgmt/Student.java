package student_mgmt;

import java.util.Scanner;

import admin_mgmt.*;

public class Student extends User {
    private String studentNo;
    private Scanner scanner;
    
    public Student() {
        scanner = new Scanner(System.in);
    }

    public Student(String username, String password) {
        super(username, password);
    }

    public Student(String username, String password, String studentNo) {
        super(username, password);
        this.studentNo = studentNo;
    }

    public void StudentPanel() {

        mainMenu: while (true) {
            System.out.println("Enter Username:");
            String username_input = scanner.nextLine();
            System.out.println("Enter Password:");
            String password_input = scanner.nextLine();

            if (tt_admin.database.getUsers().isEmpty()) {
                System.out.println("No students in the database, please go to the admin panel to add students!");
                break mainMenu;
            }
            for (admin_mgmt.User user : tt_admin.database.getUsers()) {
                if (username_input.equals(user.getUsername())) {
                    if (password_input.equals(user.getPassword())) {
                        System.out.println("Welcome, " + user.getUsername());
                        System.out.println("1. Log Out");
                        System.out.println("2. Quit");
                        System.out.print("Enter your choice: ");

                        switch (scanner.nextInt()) {
                            case 1:
                                break mainMenu;
                            case 2:
                                System.out.println("Exiting the student panel..");
                                System.exit(0);
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                        }
                    } else {
                        while (true) {
                            System.out.println("Wrong passward, try again? (y/n)");
                            String input = scanner.nextLine();
                            if (input.toLowerCase().equals("y")) {
                                break;
                            }
                            if (input.toLowerCase().equals("n")) {
                                break mainMenu;
                            } else {
                                System.out.println("Invalid choice. Please enter a valid option.");
                            }
                        }
                    }
                } else {
                    System.out.println("User not exist, try again? (y/n)");
                    String input = scanner.nextLine();
                    if (input.toLowerCase().equals("y")) {
                        continue;
                    }
                    if (input.toLowerCase().equals("n")) {
                        break mainMenu;
                    } else {
                        System.out.println("Invalid choice. Please enter a valid option.");
                    }
                }
            }
        }
    }

}