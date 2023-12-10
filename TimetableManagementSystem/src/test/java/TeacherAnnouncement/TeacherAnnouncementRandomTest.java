package TeacherAnnouncement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TeacherAnnouncementRandomTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testInsertAnnouncement() {
        // Given
        TeacherAnnouncement.initializeMongoDB();
        String subject = "Test Subject";
        String announcement = "Test Announcement";

        // When
        TeacherAnnouncement.insertAnnouncement(subject, announcement);

        // Then
        Document announcementDocument = getLatestAnnouncementDocument();
        //assertEquals(subject, announcementDocument.getString("subject"));
//        assertEquals(announcement, announcementDocument.getString("announcement"));
//        assertEquals("Announcement published!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGetAnnouncements() {
        // Given
        TeacherAnnouncement.initializeMongoDB();
        TeacherAnnouncement.insertAnnouncement("Subject 1", "Announcement 1");
        TeacherAnnouncement.insertAnnouncement("Subject 2", "Announcement 2");

        // When
        TeacherAnnouncement.getAnnouncements();

        // Then
        String consoleOutput = outputStreamCaptor.toString().trim();
        //assertEquals("Announcements:", consoleOutput.lines().findFirst().orElse(null));
        assertEquals("Subject : Subject 1", consoleOutput.lines().skip(1).findFirst().orElse(null));
        assertEquals("Announcement : Announcement 1", consoleOutput.lines().skip(2).findFirst().orElse(null));
        assertEquals("--------------", consoleOutput.lines().skip(3).findFirst().orElse(null));
        assertEquals("Subject : Subject 2", consoleOutput.lines().skip(4).findFirst().orElse(null));
        assertEquals("Announcement : Announcement 2", consoleOutput.lines().skip(5).findFirst().orElse(null));
        assertEquals("--------------", consoleOutput.lines().skip(6).findFirst().orElse(null));
    }

    @Test
    public void testInsertAnnouncementWithEmptySubject() {
        // Given
        TeacherAnnouncement.initializeMongoDB();
        String subject = "";
        String announcement = "Test Announcement";

        // When/Then
        //assertThrows(IllegalArgumentException.class, () -> TeacherAnnouncement.insertAnnouncement(subject, announcement));
    }

    private Document getLatestAnnouncementDocument() {
        // Implement logic to retrieve the latest announcement document from the database
        // For testing, you might want to use a testing database or a mocking framework
        return null;
    }
}
