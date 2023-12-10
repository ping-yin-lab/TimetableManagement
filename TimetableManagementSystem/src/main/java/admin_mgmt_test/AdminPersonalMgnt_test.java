package admin_mgmt_test;

import admin_mgmt.tt_admin.Schedule;
import admin_mgmt.tt_admin.scheduleDatabase;
import teacher.Personal_Schedule;

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

class AdminPersonalMgnt_test {

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
    void Specification_testCreatePersonalASchedule() {
    	Schedule realSchedule = new Schedule(1, "JingleJingle", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");
        scheduleDB.addPersoanlAdmin(realSchedule);
        assertTrue(scheduleDB.displaySpecific("JingleJingle", "PersonalA"));
    }
    
    @Test
    void Specification_testUpdatePersonalASchedule() {
        Schedule realSchedule = new Schedule(1, "Lacking", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");
        scheduleDB.addPersoanlAdmin(realSchedule);
        realSchedule.setSchedulename("Stable");
        realSchedule.setStarttime(LocalDateTime.now().plusDays(1));
        realSchedule.setEndtime(LocalDateTime.now().plusDays(1).plusHours(2));
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Lacking"),
            Filters.eq("type", "PersonalA")
        );
        scheduleDB.updatePersonalAdmin(realSchedule, filter);
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Stable"),
            Filters.eq("type", "PersonalA")
        );
        assertTrue(scheduleDB.displaySpecific("Stable", "PersonalA"));
    }
    //Delete PersonalA schedule
    @Test
    void Specification_testDeletePersonalASchedule() {
        Schedule realSchedule = new Schedule(1, "Poochyeena", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");
        scheduleDB.addPersoanlAdmin(realSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", 1),
            Filters.eq("title", "Poochyeena"),
            Filters.eq("type", "PersonalA")
        );
        scheduleDB.deletePersonalAdmin(filter);
        long count = scheduleDB.SDCollection.countDocuments(filter);
        assertEquals(0, count, "PersonalA schedule should be deleted from the database");
    }
    //Display PersonalA schedule
    @Test
    void Specification_testDisplayPersonalASchedule() {
        assertTrue(scheduleDB.displayResult("PersonalA"));
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
    void testCreateRandomPersonalASchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "PersonalA");
        scheduleDB.addPersoanlAdmin(randomSchedule);
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "PersonalA")
        );
        assertTrue(scheduleDB.displaySpecific(title, "PersonalA"));
    }
    //Random Update
    @Test
    void testUpdateRandomPersonalASchedule() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "PersonalA");
        scheduleDB.addPersoanlAdmin(randomSchedule);

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
            Filters.eq("type", "PersonalA")
        );
        scheduleDB.updatePersonalAdmin(randomSchedule, filter);

        // Verify that the schedule was updated successfully
        Bson updatedFilter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", updatedTitle),
            Filters.eq("type", "PersonalA")
        );
        long count = scheduleDB.SDCollection.countDocuments(updatedFilter);
        assertEquals(1, count, "PersonalA schedule should be updated in the database");
    }

    //Random delete
    @Test
    void testDeleteRandomPersonalA() {
        // Generate random inputs
        int userId = generateRandomUserId();
        String title = generateRandomTitle();
        LocalDateTime startTime = generateRandomStartTime();
        LocalDateTime endTime = generateRandomEndTime();

        // Create a real Personal_Schedule object and add it to the database
        Schedule randomSchedule = new Schedule(userId, title, startTime, endTime, "PersonalA");
        scheduleDB.addPersoanlAdmin(randomSchedule);

        // Call the delete PersonalA method
        Bson filter = Filters.and(
            Filters.eq("userid", userId),
            Filters.eq("title", title),
            Filters.eq("type", "PersonalA")
        );
        scheduleDB.deletePersonalAdmin(filter);
        
        // Verify that the schedule was deleted successfully
        assertFalse(scheduleDB.displaySpecific(title, "PersonalA"));
    }
    /////////////Statement-based/////////////////
 // No assertion needed for statement coverage; we are mainly checking for exceptions
    @Test
    void Statement_testCreatePersonalACoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songkran", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");

        // Call the add PersonalA Schedule method
        scheduleDB.addPersoanlAdmin(schedule);
    }
    
    @Test
    void Statement_testDisplayPersonalACoverage() {
        // Call the display PersonalA method
        scheduleDB.displayPersonalAdmin();
    }
    @Test
    void Statement_testUpdatePersonalACoverage() {
        // Create a real Personal_Schedule object
        Schedule schedule = new Schedule(1, "Songteen", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");

        // Call the updatePersonalSchedule method
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updatePersonalAdmin(schedule, filter);
    }
    @Test
    void Statement_testDeletePersonalACoverage() {
        Bson filter = Filters.eq("title", "Songteen");
        scheduleDB.deletePersonalAdmin(filter);
    }
    ////////////////Branch-Based/////////////////////////
    @Test
    void Branch_testCreatePersonalAScheduleCoverage() {
        Schedule schedule = new Schedule(1, "PeterParkerBirthday", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");
        scheduleDB.addPersoanlAdmin(schedule);
    }
    
    @Test
    void Branch_testDisplayPersonalACoverage() {
        scheduleDB.displayPersonalAdmin();
    }
    @Test
    void Branch_UpdatePersonalACoverage() {
        Schedule schedule = new Schedule(1, "RainTooMuch", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalA");
        Bson filter = Filters.eq("userid", 1);
        scheduleDB.updatePersonalAdmin(schedule, filter);
    }
    @Test
    void Branch_testDeletePersonalACoverage() {
        Bson filter = Filters.eq("title", "RainTooMuch");
        scheduleDB.deletePersonalAdmin(filter);
    }
}
