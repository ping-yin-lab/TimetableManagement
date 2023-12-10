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

class AdminCEventMgnt_test {

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
    void Specification_testCreateCEventSchedule() {
    	Schedule realSchedule = new Schedule(1, "JingleJingle", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");
        scheduleDB.addCEvent(realSchedule);
        assertTrue(scheduleDB.displaySpecific("JingleJingle", "Event"));
    }
    
    @Test
    void Specification_testUpdateCEventSchedule() {
        Schedule realSchedule = new Schedule(1, "Lacking", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");
        scheduleDB.addCEvent(realSchedule);
        realSchedule.setSchedulename("Stable");
        realSchedule.setStarttime(LocalDateTime.now().plusDays(1));
        realSchedule.setEndtime(LocalDateTime.now().plusDays(1).plusHours(2));
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Lacking"),
            Filters.eq("type", "Event")
        );
        scheduleDB.updateCEvent(realSchedule, filter);
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Stable"),
            Filters.eq("type", "Event")
        );
        assertTrue(scheduleDB.displaySpecific("Stable", "Event"));
    }
    //Delete CEvent schedule
    @Test
    void Specification_testDeleteCEventSchedule() {
        Schedule realSchedule = new Schedule(1, "Poochyeena", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");
        scheduleDB.addCEvent(realSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Poochyeena"),
            Filters.eq("type", "Event")
        );
        scheduleDB.deleteCEvent(filter);
        long count = scheduleDB.SDCollection.countDocuments(filter);
        assertEquals(0, count, "CEvent schedule should be deleted from the database");
    }
    //Display CEvent schedule
    @Test
    void Specification_testDisplayCEventSchedule() {
        assertTrue(scheduleDB.displayResult("Event"));
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
    void testCreateRandomCEventSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Event");
        scheduleDB.addCEvent(randomSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Event")
        );
        assertTrue(scheduleDB.displaySpecific(title, "Event"));
    }
    //Random Update
    @Test
    void testUpdateRandomCEventSchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Event");
        scheduleDB.addCEvent(randomSchedule);

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
            Filters.eq("type", "Event")
        );
        scheduleDB.updateCEvent(randomSchedule, filter);

        // Verify that the schedule was updated successfully
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", updatedTitle),
            Filters.eq("type", "Event")
        );
        long count = scheduleDB.SDCollection.countDocuments(updatedFilter);
        assertEquals(1, count, "CEvent schedule should be updated in the database");
    }

    //Random delete
    @Test
    void testDeleteRandomCEvent() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Event");
        scheduleDB.addCEvent(randomSchedule);

        // Call the delete CEvent method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Event")
        );
        scheduleDB.deleteCEvent(filter);
        
        // Verify that the schedule was deleted successfully
        assertFalse(scheduleDB.displaySpecific(title, "Event"));
    }
    /////////////Statement-based/////////////////
 // No assertion needed for statement coverage; we are mainly checking for exceptions
    @Test
    void Statement_testCreateCEventCoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songkran", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");

        // Call the add CEvent Schedule method
        scheduleDB.addCEvent(schedule);
    }
    
    @Test
    void Statement_testDisplayCEventCoverage() {
        // Call the display CEvent method
        scheduleDB.displayCEvent();
    }
    @Test
    void Statement_testUpdateCEventCoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songteen", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");

        // Call the updatePersonalSchedule method
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updateCEvent(schedule, filter);
    }
    @Test
    void Statement_testDeleteCEventCoverage() {
        Bson filter = Filters.eq("title", "Songteen");
        scheduleDB.deleteCEvent(filter);
    }
    ////////////////Branch-Based/////////////////////////
    @Test
    void Branch_testCreateCEventScheduleCoverage() {
        Schedule schedule = new Schedule(1, "PeterParkerBirthday", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");
        scheduleDB.addCEvent(schedule);
    }
    
    @Test
    void Branch_testDisplayCEventCoverage() {
        scheduleDB.displayCEvent();
    }
    @Test
    void Branch_UpdateCEventCoverage() {
        Schedule schedule = new Schedule(1, "RainTooMuch", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Event");
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updateCEvent(schedule, filter);
    }
    @Test
    void Branch_testDeleteCEventCoverage() {
        Bson filter = Filters.eq("title", "RainTooMuch");
        scheduleDB.deleteCEvent(filter);
    }
}
