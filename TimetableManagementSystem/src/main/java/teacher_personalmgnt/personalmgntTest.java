package teacher_personalmgnt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class personalmgntTest {
	@Test
    public void testAddPersonalSchedule() {
        Personal_Schedule psche = new Personal_Schedule(1, "Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "PersonalT");
        assertDoesNotThrow(() -> personalmgnt.addPersonalSchedule(psche));
        // You can add additional assertions to check the state of the scheduleCollection after adding a personal schedule
    }

}
