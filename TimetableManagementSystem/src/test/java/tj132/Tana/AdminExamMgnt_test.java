package tj132.Tana;

import tj132.Tana.tt_admin.Schedule;
import tj132.Tana.tt_admin.scheduleDatabase;
import tj132.Tana.Personal_Schedule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.model.Filters;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.random.*;

import org.junit.jupiter.api.Test;

class AdminExamMgnt_test {

	private scheduleDatabase scheduleDB;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Assuming you have a method to initialize the scheduleDB, replace it with your actual initialization logic
    	scheduleDB = new scheduleDatabase();
    	
    	OutputStream outputStreamCaptor = null;
		System.setOut(new PrintStream(outputStreamCaptor, true, StandardCharsets.UTF_8));
    }
    ///////////////Specification based///////////////////
    @Test
    void Specification_testCreateExamSchedule() {
    	Schedule realSchedule = new Schedule(1, "JingleJingle", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");
        scheduleDB.addExamSchedule(realSchedule);
        assertTrue(scheduleDB.displaySpecific("JingleJingle", "Exam"));
    }
    
    @Test
    void Specification_testUpdateExamSchedule() {
        Schedule realSchedule = new Schedule(1, "Slowking", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");
        scheduleDB.addExamSchedule(realSchedule);
        realSchedule.setSchedulename("FastKing");
        realSchedule.setStarttime(LocalDateTime.now().plusDays(1));
        realSchedule.setEndtime(LocalDateTime.now().plusDays(1).plusHours(2));
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Slowking"),
            Filters.eq("type", "Exam")
        );
        scheduleDB.updateExam(realSchedule, filter);
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "FastKing"),
            Filters.eq("type", "Exam")
        );
        assertTrue(scheduleDB.displaySpecific("FastKing", "Exam"));
    }
    //Delete Exam schedule
    @Test
    void Specification_testDeleteExamSchedule() {
        Schedule realSchedule = new Schedule(1, "Poochyeena", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");
        scheduleDB.addExamSchedule(realSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Poochyeena"),
            Filters.eq("type", "Exam")
        );
        scheduleDB.deleteexam(filter);
        long count = scheduleDB.SDCollection.countDocuments(filter);
        assertEquals(0, count, "Exam schedule should be deleted from the database");
    }
    //Display Exam schedule
    @Test
    void Specification_testDisplayExamSchedule() {
        assertTrue(scheduleDB.displayResult("Exam"));
    }
    
    /////////////random-based //////////////////////////////
 // Helper methods for generating random data
    private int generateRandomUserId() {
        return new Random().nextInt(1000) + 1; // Assuming userId is a positive integer
    }

    private String generateRandomTitle() {
        return "RandomTitle" + new Random().nextInt(1000);
    }

    private LocalDateTime generateRandomStartTime() {
        return LocalDateTime.now().plusDays(new Random().nextInt(30));
    }

    private LocalDateTime generateRandomEndTime() {
        return LocalDateTime.now().plusDays(new Random().nextInt(30)).plusHours(2);
    }
    
    //Random create
    @Test
    void testCreateRandomExamSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Exam");
        scheduleDB.addExamSchedule(randomSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Exam")
        );
        assertTrue(scheduleDB.displaySpecific(title, "Exam"));
    }
    //Random Update
    @Test
    void testUpdateRandomExamSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Exam");
        scheduleDB.addExamSchedule(randomSchedule);

        // Generate random data for update
        String updatedTitle = generateRandomTitle();
        LocalDateTime updatedStartTime = generateRandomStartTime();

        // Update the schedule
        randomSchedule.setSchedulename(updatedTitle);
        randomSchedule.setStarttime(updatedStartTime);

        // Call the updatePersonalSchedule method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Exam")
        );
        scheduleDB.updateExam(randomSchedule, filter);

        // Verify that the schedule was updated successfully
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", updatedTitle),
            Filters.eq("type", "Exam")
        );
        long count = scheduleDB.SDCollection.countDocuments(updatedFilter);
        assertEquals(1, count, "Exam schedule should be updated in the database");
    }

    //Random delete
    @Test
    void testDeleteRandomExam() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Exam");
        scheduleDB.addExamSchedule(randomSchedule);

        // Call the delete Exam method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Exam")
        );
        scheduleDB.deleteexam(filter);
        
        // Verify that the schedule was deleted successfully
        assertFalse(scheduleDB.displaySpecific(title, "Exam"));
    }
    /////////////Statement-based/////////////////
 // No assertion needed for statement coverage; we are mainly checking for exceptions
    @Test
    void Statement_testCreateExamCoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songkran", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");

        // Call the add Exam Schedule method
        scheduleDB.addExamSchedule(schedule);
        
    }
    
    @Test
    void Statement_testDisplayExamCoverage() {
        // Call the display Exam method
        scheduleDB.displayExam();
    }
    @Test
    void Statement_testUpdateExamCoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songteen", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");

        // Call the updatePersonalSchedule method
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updateExam(schedule, filter);
    }
    @Test
    void Statement_testDeleteExamCoverage() {
        Bson filter = Filters.eq("title", "Songteen");
        scheduleDB.deleteexam(filter);
    }
    ////////////////Branch-Based/////////////////////////
    @Test
    void Branch_testCreateExamScheduleCoverage() {
        Schedule schedule = new Schedule(1, "PeterParkerBirthday", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");
        scheduleDB.addExamSchedule(schedule);
    }
    
    @Test
    void Branch_testDisplayExamCoverage() {
        scheduleDB.displayExam();
    }
    @Test
    void Branch_UpdateExamCoverage() {
        Schedule schedule = new Schedule(1, "RainTooMuch", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Exam");
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updateExam(schedule, filter);
    }
    @Test
    void Branch_testDeleteExamCoverage() {
        Bson filter = Filters.eq("title", "RainTooMuch");
        scheduleDB.deleteexam(filter);
    }
}
