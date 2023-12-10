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

class AdminHolidayMgnt_test {

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
    void Specification_testCreateHolidaySchedule() {
    	Schedule realSchedule = new Schedule(1, "JingleJingle", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");
        scheduleDB.addHoliday(realSchedule);
        assertTrue(scheduleDB.displaySpecific("JingleJingle", "Holiday"));
    }
    
    @Test
    void Specification_testUpdateHolidaySchedule() {
        Schedule realSchedule = new Schedule(1, "Lacking", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");
        scheduleDB.addHoliday(realSchedule);
        realSchedule.setSchedulename("Stable");
        realSchedule.setStarttime(LocalDateTime.now().plusDays(1));
        realSchedule.setEndtime(LocalDateTime.now().plusDays(1).plusHours(2));
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Lacking"),
            Filters.eq("type", "Holiday")
        );
        scheduleDB.updateHoliday(realSchedule, filter);
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Stable"),
            Filters.eq("type", "Holiday")
        );
        assertTrue(scheduleDB.displaySpecific("Stable", "Holiday"));
    }
    //Delete Holiday schedule
    @Test
    void Specification_testDeleteHolidaySchedule() {
        Schedule realSchedule = new Schedule(1, "Poochyeena", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");
        scheduleDB.addHoliday(realSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Poochyeena"),
            Filters.eq("type", "Holiday")
        );
        scheduleDB.deleteHoliday(filter);
        long count = scheduleDB.SDCollection.countDocuments(filter);
        assertEquals(0, count, "Holiday schedule should be deleted from the database");
    }
    //Display Holiday schedule
    @Test
    void Specification_testDisplayHolidaySchedule() {
        assertTrue(scheduleDB.displayResult("Holiday"));
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
    void testCreateRandomHolidaySchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Holiday");
        scheduleDB.addHoliday(randomSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Holiday")
        );
        assertTrue(scheduleDB.displaySpecific(title, "Holiday"));
    }
    //Random Update
    @Test
    void testUpdateRandomHolidaySchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Holiday");
        scheduleDB.addHoliday(randomSchedule);

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
            Filters.eq("type", "Holiday")
        );
        scheduleDB.updateHoliday(randomSchedule, filter);

        // Verify that the schedule was updated successfully
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", updatedTitle),
            Filters.eq("type", "Holiday")
        );
        long count = scheduleDB.SDCollection.countDocuments(updatedFilter);
        assertEquals(1, count, "Holiday schedule should be updated in the database");
    }

    //Random delete
    @Test
    void testDeleteRandomHoliday() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "Holiday");
        scheduleDB.addHoliday(randomSchedule);

        // Call the delete Holiday method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "Holiday")
        );
        scheduleDB.deleteHoliday(filter);
        
        // Verify that the schedule was deleted successfully
        assertFalse(scheduleDB.displaySpecific(title, "Holiday"));
    }
    /////////////Statement-based/////////////////
 // No assertion needed for statement coverage; we are mainly checking for exceptions
    @Test
    void Statement_testCreateHolidayCoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songkran", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");

        // Call the add Holiday Schedule method
        scheduleDB.addHoliday(schedule);
    }
    
    @Test
    void Statement_testDisplayHolidayCoverage() {
        // Call the display Holiday method
        scheduleDB.displayHoliday();
    }
    @Test
    void Statement_testUpdateHolidayCoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songteen", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");

        // Call the updatePersonalSchedule method
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updateHoliday(schedule, filter);
    }
    @Test
    void Statement_testDeleteHolidayCoverage() {
        Bson filter = Filters.eq("title", "Songteen");
        scheduleDB.deleteHoliday(filter);
    }
    ////////////////Branch-Based/////////////////////////
    @Test
    void Branch_testCreateHolidayScheduleCoverage() {
        Schedule schedule = new Schedule(1, "PeterParkerBirthday", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");
        scheduleDB.addHoliday(schedule);
    }
    
    @Test
    void Branch_testDisplayHolidayCoverage() {
        scheduleDB.displayHoliday();
    }
    @Test
    void Branch_UpdateHolidayCoverage() {
        Schedule schedule = new Schedule(1, "RainTooMuch", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Holiday");
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updateHoliday(schedule, filter);
    }
    @Test
    void Branch_testDeleteHolidayCoverage() {
        Bson filter = Filters.eq("title", "RainTooMuch");
        scheduleDB.deleteHoliday(filter);
    }
}
