package xw256.studentmgnt;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;

import xw256.Database.Databases;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class StudentScheduleActions {

    public static void AddStudentSchedule(StudentSchedule ss) {
        Document userDocument = new Document("student id", ss.studentId)
                .append("title", ss.getSchedulename())
                .append("start time", ss.getStarttime())
                .append("end time", ss.getEndtime())
                .append("type", ss.getType());
        // validate if local datetime is overlapped with existed schedules
        if (ValidateSchedule(ss)) {
            Databases.STUDENT_SCHE_DATABASE().insertOne(userDocument);
            System.out.println("Schedule added successfully!\n");
        } else {
            System.out.println("Add schedule failed, please choose another available time.\n");
        }
    }

    public static void UpdateStudentSchedule(StudentSchedule ss, Bson updateFilter) {
        Document updateDoc = new Document("userid", ss.getUserid())
                .append("title", ss.getSchedulename())
                .append("start time", ss.getStarttime())
                .append("end time", ss.getEndtime())
                .append("type", ss.getType());

        if (ValidateSchedule(ss)) {
            Databases.STUDENT_SCHE_DATABASE().insertOne(updateDoc);
            System.out.println("Schedule updated successfully!");
            Bson updateop = new Document("$set", updateDoc);
            Databases.STUDENT_SCHE_DATABASE().updateOne(updateFilter, updateop);
        } else {
            System.out.println("Update schedule failed, please choose another available time.\n");
        }
    }

    public static void DisplaySchedule() {
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

    public static boolean ValidateSchedule(StudentSchedule ss) {
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

            // if (sdt.compareTo(scheduledSdt)>=0&&sdt.compareTo(scheduledEdt)>0 ||
            // edt.compareTo(scheduledSdt)
            // ) {

            // }
            if (sdt.equals(scheduledSdt) || sdt.equals(edt) ||
                    sdt.isAfter(scheduledSdt) && sdt.isBefore(scheduledEdt) ||
                    edt.isAfter(scheduledSdt) && edt.isBefore(scheduledEdt) ||
                    sdt.isBefore(scheduledSdt) && edt.isAfter(scheduledEdt)) {
                return false;
            }
        }
        while (st_cursor.hasNext()) {
            Document document = st_cursor.next();
            LocalDateTime scheduledSdt = ((Date) document.get("start time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime scheduledEdt = ((Date) document.get("end time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (sdt.equals(scheduledSdt) ||
                    sdt.equals(edt) ||
                    edt.equals(scheduledEdt) ||
                    sdt.isAfter(scheduledSdt) && sdt.isBefore(scheduledEdt) ||
                    edt.isAfter(scheduledSdt) && edt.isBefore(scheduledEdt) ||
                    sdt.isBefore(scheduledSdt) && edt.isAfter(scheduledEdt)) {
                return false;
            }
        }
        return true;
    }

    public static void DisplaySchedulewithID() {
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
}
