package student_mgmt;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;

import com.mongodb.client.model.Filters;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Student_Management {

    public static void AddStudentSchedule(Student_Schedule ss) {
        Document userDocument = new Document("student id", ss.studentId)
                .append("title", ss.getSchedulename())
                .append("start time", ss.getStarttime())
                .append("end time", ss.getEndtime())
                .append("type", ss.getType());
        // validate if local datetime is overlapped with existed schedules
        if (ValidateSchedule(ss)) {
            Databases.STUDENT_SCHE_DATABASE().insertOne(userDocument);
            System.out.println("Schedule added successfully!");
        } else {
            System.out.println("Add schedule failed, please choose another time available.");
        }
    }

    public static void UpdateStudentSchedule(Student_Schedule ss, Bson Updatefilter) {
        Document updateDoc = new Document("userid", ss.getUserid())
                .append("title", ss.getSchedulename())
                .append("start time", ss.getStarttime())
                .append("end time", ss.getEndtime())
                .append("type", ss.getType());

        if (ValidateSchedule(ss)) {
            Databases.STUDENT_SCHE_DATABASE().insertOne(updateDoc);
            System.out.println("Schedule updated successfully!");
            Bson updateop = new Document("$set", updateDoc);
            Databases.STUDENT_SCHE_DATABASE().updateOne(Updatefilter, updateop);
        } else {
            System.out.println("Update schedule failed, please choose another time available.");
        }

    }

    public static void displaySchedule() {
        System.out.println("List of Schedule:");
        Databases.TEACHER_SCHE_DATABASE().find()
                .forEach(document -> System.out
                        .println("Teacher Schedule" + "\n Title: " + document.get("title") + "\n Start time: "
                                + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));

        Databases.STUDENT_SCHE_DATABASE().find()
                .forEach(document -> System.out
                        .println("Student Schedule" + "\nTitle: " + document.get("title") + "\n Start time: "
                                + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));
    }

    public static boolean ValidateSchedule(Student_Schedule ss) {
        MongoCursor<Document> st_cursor = Databases.STUDENT_SCHE_DATABASE().find().iterator();
        MongoCursor<Document> te_cursor = Databases.TEACHER_SCHE_DATABASE().find().iterator();

        LocalDateTime sdt = ss.getStarttime();
        LocalDateTime edt = ss.getEndtime();
        while (te_cursor.hasNext()) {
            Document document = te_cursor.next();
            LocalDateTime scheduledSdt = ((Date) document.get("start time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime scheduledEdt = ((Date) document.get("end time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (sdt.equals(edt) ||
                    sdt.isAfter(scheduledSdt) && sdt.isBefore(scheduledEdt) ||
                    edt.isAfter(scheduledEdt) && edt.isBefore(scheduledEdt) ||
                    sdt.isBefore(scheduledEdt) && edt.isBefore(scheduledEdt)) {
                return false;
            }
        }
        while (st_cursor.hasNext()) {
            Document document = st_cursor.next();
            LocalDateTime scheduledSdt = ((Date) document.get("start time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime scheduledEdt = ((Date) document.get("end time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (sdt.equals(edt) ||
                    sdt.isAfter(scheduledSdt) && sdt.isBefore(scheduledEdt) ||
                    edt.isAfter(scheduledEdt) && edt.isBefore(scheduledEdt) ||
                    sdt.isBefore(scheduledEdt) && edt.isBefore(scheduledEdt)) {
                return false;
            }
        }
        return true;
    }

    public static void displaySchedulewithID() {
        System.out.println("List of Schedule:");
        AtomicInteger s_index = new AtomicInteger(1);
        AtomicInteger t_index = new AtomicInteger(1);

        Databases.STUDENT_SCHE_DATABASE().find().forEach(document -> System.out.println(s_index.getAndIncrement()
                + "  Id : " + document.get("_id")
                + "\nTitle: " + document.get("title")
                + "\nStart time: " + document.get("start time")
                + "\nEnd time: " + document.get("end time")
                + "\n==========================\n"));

        Databases.TEACHER_SCHE_DATABASE().find().forEach(document -> System.out.println(t_index.getAndIncrement()
                + "  Id : " + document.get("_id")
                + "\nTitle: " + document.get("title")
                + "\nStart time: " + document.get("start time")
                + "\nEnd time: " + document.get("end time")
                + "\n==========================\n"));

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Menu: while (true) {
            System.out.println("Welcome to Student System");
            System.out.println("==========================");
            System.out.println("Choose the menu below");
            System.out.println("1. Student Schedule Management");
            System.out.println("2. Send Message");
            System.out.println("3. Send Request");
            System.out.println("4. Quit");
            System.out.print("Input : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("Student Schedule Management");
                        System.out.println("Your Schedule");
                        System.out.println("==========================");
                        displaySchedule();
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
                                Student_Schedule sSche = new Student_Schedule(1, stitle, sdt, edt, "Student");
                                AddStudentSchedule(sSche);
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
                                Student_Schedule Updatepsche = new Student_Schedule(1, stitle, sdt, edt, "Student");
                                UpdateStudentSchedule(Updatepsche, Updatefilter);

                                break;
                            case 3:
                                displaySchedulewithID();
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
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;

            }
        }

    }

}