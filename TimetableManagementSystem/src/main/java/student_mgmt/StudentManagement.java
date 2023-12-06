package student_mgmt;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;

import com.mongodb.client.model.Filters;

import Database.Databases;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class StudentManagement {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Student System");
            System.out.println("==========================");
            System.out.println("Choose the menu below");
            System.out.println("1. Student Schedule Management");
            System.out.println("2. Message System");
            System.out.println("3. Request System");
            System.out.println("4. Quit");
            System.out.print("Input : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    StudentManagementSystem: while (true) {
                        System.out.println("Student Schedule Management");
                        System.out.println("Your Schedule");
                        System.out.println("==========================");
                        StudentScheduleActions.DisplaySchedule();
                        System.out.println("1. Add new Student Schedule");
                        System.out.println("2. Update schedule");
                        System.out.println("3. Delete schedule");
                        System.out.println("4. Back to main menu");
                        System.out.println("5. Quit");
                        System.out.print("Input : ");
                        int stScheChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (stScheChoice) {
                            case 1:
                                System.out.println("Enter Schedule title: ");
                                String stitle = scanner.nextLine();
                                System.out.println("==Input Starting time==");
                                System.out.print("Enter Date in format yyyy-mm-dd :");
                                String stdate = scanner.nextLine();
                                System.out.print("Enter Starting time in format HH:mm :");
                                String sttime = scanner.nextLine();
                                LocalDateTime sdt = LocalDateTime.parse(stdate + "T" + sttime);
                                System.out.println("Your startting time : " + sdt);
                                System.out.println("==Input Ending time==");
                                System.out.print("Enter Date in format yyyy-mm-dd :");
                                String eddate = scanner.nextLine();
                                System.out.print("Enter Ending time in format HH:mm :");
                                String edtime = scanner.nextLine();
                                LocalDateTime edt = LocalDateTime.parse(eddate + "T" + edtime);
                                System.out.println("Your Ending time : " + edt);
                                StudentSchedule sSche = new StudentSchedule(1, stitle, sdt, edt, "Student");
                                StudentScheduleActions.AddStudentSchedule(sSche);
                                break;
                            case 2:
                                System.out.println("Updating exist schedule");
                                System.out.print("Enter Student Schedule title you want to change: ");
                                String target = scanner.nextLine();
                                Bson Updatefilter = Filters.eq("title", target);

                                System.out.println("New Title : ");
                                stitle = scanner.nextLine();
                                System.out.println("==Input New Starting time==");
                                System.out.print("Enter Date in format yyyy-mm-dd :");
                                stdate = scanner.nextLine();
                                System.out.print("Enter Starting time in format HH:mm :");
                                sttime = scanner.nextLine();
                                sdt = LocalDateTime.parse(stdate + "T" + sttime);
                                System.out.println("==Input New Ending time==");
                                System.out.print("Enter Date in format yyyy-mm-dd :");
                                eddate = scanner.nextLine();
                                System.out.print("Enter Ending time in format HH:mm :");
                                edtime = scanner.nextLine();
                                edt = LocalDateTime.parse(eddate + "T" + edtime);
                                StudentSchedule Updatepsche = new StudentSchedule(1, stitle, sdt, edt, "Student");
                                StudentScheduleActions.UpdateStudentSchedule(Updatepsche, Updatefilter);

                                break;
                            case 3:
                                StudentScheduleActions.DisplaySchedulewithID();
                                System.out.println("Enter the title of which item you want to delete : ");
                                String title = scanner.nextLine();
                                Bson filter = Filters.eq("title", title);
                                Databases.STUDENT_SCHE_DATABASE().deleteOne(filter);
                                System.out.println("Deletion successfully");
                                break;
                            case 4:
                                break;
                            case 5:
                                System.exit(0);
                            default:
                                break;
                        }
                        break;
                    }
                    break;
                case 2:
                    MsgSystem: while (true) {
                        while (true) {
                            System.out.println("Message System");
                            System.out.println("==========================");
                            System.out.println("1. Display Messages");
                            System.out.println("2. Send a Message");
                            System.out.println("3. Delete a Message");
                            System.out.println("4. Back to main menu");
                            System.out.println("5. Quit");
                            System.out.print("Input : ");
                            int msgChoice = scanner.nextInt();
                            scanner.nextLine();
                            switch (msgChoice) {
                                case 1:
                                    MessageActions.DisplayMessage();
                                    break;
                                case 2:
                                    System.out.println("Enter the name of receiver: ");
                                    String receiver = scanner.nextLine();
                                    System.out.print("Message Title: ");
                                    String title = scanner.nextLine();
                                    System.out.println("Message Content: ");
                                    String content = scanner.nextLine();
                                    System.out.println("Are you sure to send this message? (y/n)");
                                    String sendMsgChoice = scanner.nextLine().toLowerCase();
                                    switch (sendMsgChoice) {
                                        case "y":
                                            LocalDateTime stamp = LocalDateTime.now();
                                            Message m = new Message("1", title, content, stamp, "Student 1", receiver);
                                            MessageActions.WriteMessage(m);
                                            break;
                                        case "n":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 3:
                                    MessageActions.DisplayMessage();
                                    System.out.println("Enter the title of which message you want to delete : ");
                                    String msgTitle = scanner.nextLine();
                                    Bson filter = Filters.eq("title", msgTitle);
                                    Databases.STUDENT_SCHE_DATABASE().deleteOne(filter);
                                    System.out.println("Message delete successfully");
                                    break;
                                case 4:
                                    break MsgSystem;
                                case 5:
                                    System.exit(0);
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case 3:
                    RequestSystem: while (true) {
                        while (true) {
                            System.out.println("Request System");
                            System.out.println("==========================");
                            System.out.println("1. Display requests sent");
                            System.out.println("2. Send a request to Admin");
                            System.out.println("3. Back to main menu");
                            System.out.println("4. Quit");
                            System.out.print("Input : ");

                            int requestChoice = scanner.nextInt();
                            scanner.nextLine();
                            switch (requestChoice) {
                                case 1:
                                    RequestActions.DisplayRequest();
                                    break;
                                case 2:
                                    System.out.println("Request to the admin: ");
                                    String content = scanner.nextLine();
                                    System.out.println("Are you sure to send this request? (y/n)");
                                    String sendMsgChoice = scanner.nextLine().toLowerCase();
                                    switch (sendMsgChoice) {
                                        case "y":
                                            LocalDateTime stamp = LocalDateTime.now();
                                            Request m = new Request("1", content, stamp, "Student 1");
                                            RequestActions.WriteRequest(m);    
                                            break;
                                        case "n":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 3:
                                    break RequestSystem;
                                case 4:
                                    System.exit(0);
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case 4:
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}