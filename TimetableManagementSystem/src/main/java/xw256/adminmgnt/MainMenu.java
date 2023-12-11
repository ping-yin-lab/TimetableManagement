package xw256.adminmgnt;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Start();
    }

    public static void Start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Log in as: ");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            switch (scanner.nextInt()) {
                case 1:
                    tt_admin admin = new tt_admin();
                    admin.AdminPanel();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
