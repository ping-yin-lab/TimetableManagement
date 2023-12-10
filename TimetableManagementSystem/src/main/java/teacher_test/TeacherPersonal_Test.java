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

class TeacherPersonal_Test {

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
    void Specification_testCreatePersonalTSchedule() {
    	Personal_Schedule realSchedule = new Personal_Schedule(1, "CoffeeBreak", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");
    	scheduleDB.addPersonalSchedule(realSchedule);
        assertTrue(scheduleDB.displaySpecific("CoffeeBreak", "PersonalT"));
    }
    
    @Test
    void Specification_testUpdatePersonalTSchedule() {
    	Personal_Schedule realSchedule = new Personal_Schedule(1, "Lacking", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");
        scheduleDB.addPersonalSchedule(realSchedule);
        realSchedule.setSchedulename("Stable");
        realSchedule.setStarttime(LocalDateTime.now().plusDays(1));
        realSchedule.setEndtime(LocalDateTime.now().plusDays(1).plusHours(2));
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Lacking"),
            Filters.eq("type", "PersonalT")
        );
        scheduleDB.updatePersonalSchedule(realSchedule, filter);
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Stable"),
            Filters.eq("type", "PersonalT")
        );
        assertTrue(scheduleDB.displaySpecific("Stable", "PersonalT"));
    }
    //Delete PersonalT schedule
    @Test
    void Specification_testDeletePersonalTSchedule() {
    	Personal_Schedule realSchedule = new Personal_Schedule(1, "Poochyeena", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");
        scheduleDB.addPersonalSchedule(realSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Poochyeena"),
            Filters.eq("type", "PersonalT")
        );
        scheduleDB.deleteSchedule("Poochyeena","PersonalT");
        assertFalse(scheduleDB.displaySpecific("Poochyeena","PersonalT"));
    }
    //Display PersonalT schedule
    @Test
    void Specification_testDisplayPersonalTSchedule() {
        assertTrue(scheduleDB.displaySpecific("CoffeeBreak","PersonalT"));
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
    void testCreateRandomPersonalTSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();
        Personal_Schedule randomSchedule = new Personal_Schedule(userId, title, startTime, endTime, "PersonalT");
        scheduleDB.addPersonalSchedule(randomSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "PersonalT")
        );
        assertTrue(scheduleDB.displaySpecific(title, "PersonalT"));
    }
    //Random Update
    @Test
    void testUpdateRandomPersonalTSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Personal_Schedule randomSchedule = new Personal_Schedule(userId, title, startTime, endTime, "PersonalT");
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
            Filters.eq("type", "PersonalT")
        );
        scheduleDB.updatePersonalSchedule(randomSchedule, filter);

        // Verify that the schedule was updated successfully
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", updatedTitle),
            Filters.eq("type", "PersonalT")
        );
        assertTrue(scheduleDB.displaySpecific(updatedTitle,"PersonalT"));
    }

    //Random delete
    @Test
    void testDeleteRandomPersonalT() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Personal_Schedule randomSchedule = new Personal_Schedule(userId, title, startTime, endTime, "PersonalT");
        scheduleDB.addPersonalSchedule(randomSchedule);

        // Call the delete PersonalT method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "PersonalT")
        );
        scheduleDB.deleteSchedule(title,"PersonalT");
        
        // Verify that the schedule was deleted successfully
        assertFalse(scheduleDB.displaySpecific(title, "PersonalT"));
    }
    /////////////Statement-based/////////////////
 // No assertion needed for statement coverage; we are mainly checking for exceptions
    @Test
    void Statement_testCreatePersonalTCoverage() {
        // Create a real Personal_Schedule object
    	Personal_Schedule schedule = new Personal_Schedule(1, "Songkran", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");

        // Call the add PersonalT Schedule method
        scheduleDB.addPersonalSchedule(schedule);
    }
    
    @Test
    void Statement_testDisplayPersonalTCoverage() {
        // Call the display PersonalT method
        scheduleDB.displaySchedule();
        scheduleDB.displaySchedulewithID();
        scheduleDB.displayPersonalT();
    }
    @Test
    void Statement_testUpdatePersonalTCoverage() {
        // Create a real Personal_Schedule object
    	Personal_Schedule schedule = new Personal_Schedule(1, "Songteen", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");

        // Call the updatePersonalSchedule method
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updatePersonalSchedule(schedule, filter);
    }
    @Test
    void Statement_testDeletePersonalTCoverage() {
        Bson filter = Filters.eq("title", "Songteen");
        scheduleDB.deleteSchedule("Songkran","PersonalT");
    }
    ////////////////Branch-Based/////////////////////////
    @Test
    void Branch_testCreatePersonalTScheduleCoverage() {
    	Personal_Schedule schedule = new Personal_Schedule(1, "PeterParkerBirthday", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");
        scheduleDB.addPersonalSchedule(schedule);
    }
    
    @Test
    void Branch_testDisplayPersonalTCoverage() {
        scheduleDB.displaySchedule();
    }
    @Test
    void Branch_UpdatePersonalTCoverage() {
    	Personal_Schedule schedule = new Personal_Schedule(1, "RainTooMuch", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updatePersonalSchedule(schedule, filter);
    }
    @Test
    void Branch_testDeletePersonalTCoverage() {
        Bson filter = Filters.eq("title", "RainTooMuch");
        scheduleDB.deleteSchedule("RainTooMuch","PersonalT");
    }
}

