package teacher_test;

import teacher.Personal_Schedule;
import teacher.personalmgnt.scheduleDatabase;

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

class TeacherTimeOffMgnt_test {

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
    void Specification_testCreateTimeOffSchedule() {
    	Personal_Schedule realSchedule = new Personal_Schedule(1, "CoffeeBreak", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");
    	scheduleDB.addPersonalSchedule(realSchedule);
        assertTrue(scheduleDB.displaySpecific("CoffeeBreak", "TimeOff"));
    }
    
    @Test
    void Specification_testUpdateTimeOffSchedule() {
    	Personal_Schedule realSchedule = new Personal_Schedule(1, "Lacking", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");
        scheduleDB.addPersonalSchedule(realSchedule);
        realSchedule.setSchedulename("Stable");
        realSchedule.setStarttime(LocalDateTime.now().plusDays(1));
        realSchedule.setEndtime(LocalDateTime.now().plusDays(1).plusHours(2));
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Lacking"),
            Filters.eq("type", "TimeOff")
        );
        scheduleDB.updatePersonalSchedule(realSchedule, filter);
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Stable"),
            Filters.eq("type", "TimeOff")
        );
        assertTrue(scheduleDB.displaySpecific("Stable", "TimeOff"));
    }
    //Delete TimeOff schedule
    @Test
    void Specification_testDeleteTimeOffSchedule() {
    	Personal_Schedule realSchedule = new Personal_Schedule(1, "Poochyeena", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");
        scheduleDB.addPersonalSchedule(realSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Poochyeena"),
            Filters.eq("type", "TimeOff")
        );
        scheduleDB.deleteSchedule("Poochyeena","TimeOff");
        assertFalse(scheduleDB.displaySpecific("Poochyeena","TimeOff"));
    }
    //Display TimeOff schedule
    @Test
    void Specification_testDisplayTimeOffSchedule() {
        assertTrue(scheduleDB.displaySpecific("CoffeeBreak","TimeOff"));
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
    void testCreateRandomTimeOffSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();
        Personal_Schedule randomSchedule = new Personal_Schedule(userId, title, startTime, endTime, "TimeOff");
        scheduleDB.addPersonalSchedule(randomSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "TimeOff")
        );
        assertTrue(scheduleDB.displaySpecific(title, "TimeOff"));
    }
    //Random Update
    @Test
    void testUpdateRandomTimeOffSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Personal_Schedule randomSchedule = new Personal_Schedule(userId, title, startTime, endTime, "TimeOff");
        scheduleDB.addPersonalSchedule(randomSchedule);

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
            Filters.eq("type", "TimeOff")
        );
        scheduleDB.updatePersonalSchedule(randomSchedule, filter);

        // Verify that the schedule was updated successfully
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", updatedTitle),
            Filters.eq("type", "TimeOff")
        );
        assertTrue(scheduleDB.displaySpecific(updatedTitle,"TimeOff"));
    }

    //Random delete
    @Test
    void testDeleteRandomTimeOff() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Personal_Schedule randomSchedule = new Personal_Schedule(userId, title, startTime, endTime, "TimeOff");
        scheduleDB.addPersonalSchedule(randomSchedule);

        // Call the delete TimeOff method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "TimeOff")
        );
        scheduleDB.deleteSchedule(title,"TimeOff");
        
        // Verify that the schedule was deleted successfully
        assertFalse(scheduleDB.displaySpecific(title, "TimeOff"));
    }
    /////////////Statement-based/////////////////
 // No assertion needed for statement coverage; we are mainly checking for exceptions
    @Test
    void Statement_testCreateTimeOffCoverage() {
        // Create a real Personal_Schedule object
    	Personal_Schedule schedule = new Personal_Schedule(1, "Songkran", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");

        // Call the add TimeOff Schedule method
        scheduleDB.addPersonalSchedule(schedule);
    }
    
    @Test
    void Statement_testDisplayTimeOffCoverage() {
        // Call the display TimeOff method
        scheduleDB.displaySchedule();
        scheduleDB.displaySchedulewithID();
        scheduleDB.displayTimeoff();
    }
    @Test
    void Statement_testUpdateTimeOffCoverage() {
        // Create a real Personal_Schedule object
    	Personal_Schedule schedule = new Personal_Schedule(1, "Songteen", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");

        // Call the updatePersonalSchedule method
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updatePersonalSchedule(schedule, filter);
    }
    @Test
    void Statement_testDeleteTimeOffCoverage() {
        Bson filter = Filters.eq("title", "Songteen");
        scheduleDB.deleteSchedule("Songkran","TimeOff");
    }
    ////////////////Branch-Based/////////////////////////
    @Test
    void Branch_testCreateTimeOffScheduleCoverage() {
    	Personal_Schedule schedule = new Personal_Schedule(1, "PeterParkerBirthday", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");
        scheduleDB.addPersonalSchedule(schedule);
    }
    
    @Test
    void Branch_testDisplayTimeOffCoverage() {
        scheduleDB.displaySchedule();
    }
    @Test
    void Branch_UpdateTimeOffCoverage() {
    	Personal_Schedule schedule = new Personal_Schedule(1, "RainTooMuch", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "TimeOff");
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updatePersonalSchedule(schedule, filter);
    }
    @Test
    void Branch_testDeleteTimeOffCoverage() {
        Bson filter = Filters.eq("title", "RainTooMuch");
        scheduleDB.deleteSchedule("RainTooMuch","TimeOff");
    }
}

