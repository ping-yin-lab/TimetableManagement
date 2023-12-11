package TimetableManagementSystem;

import java.util.Scanner;
import TimetableManagementSystem.Logins.*;

public class main {
     public static void main(String[] args) {
        clearScreen();
        Start();
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void Start() {
        Scanner scanner = new Scanner(System.in);
        AdminLogin Alogin = new AdminLogin();
        StudentLogin Slogin = new StudentLogin();
        TeacherLogin Tlogin = new TeacherLogin();

        while (true) {
            clearScreen();
            System.out.println("Welcome to Timetable Management System");
            System.out.println("=======================================");
            System.out.println("Log in as: ");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.println("3. Teacher");
            System.out.println("4.Exit");
            System.out.print("Input : ");
            switch (scanner.nextInt()) {
                case 1:
                    Alogin.adminLoginStart();
                    break;
                case 2:
                    Slogin.studentLoginStart();
                    break;
                case 3:
                    Tlogin.teacherLoginStart();
                break;
                case 4:
                    System.out.println("Thank you for using Timetable Maangement System");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}