package teacher_personalmgnt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class Personal_ScheduleTest {

	@Test
	public void testCreatePersonalSchedule() {
	    // Arrange
	    LocalDateTime startTime = LocalDateTime.now();
	    LocalDateTime endTime = startTime.plusHours(2);

	    // Act
	    Personal_Schedule schedule = new Personal_Schedule(1, "Meeting", startTime, endTime, "Work");

	    // Assert
	    assertNotNull(schedule);
	    assertEquals(1, schedule.getUserid());
	    assertEquals("Meeting", schedule.getSchedulename());
	    assertEquals(startTime, schedule.getStarttime());
	    assertEquals(endTime, schedule.getEndtime());
	    assertEquals("Work", schedule.getType());
	}
	
	@Test
	public void testUpdateScheduleName() {
	    // Arrange
	    Personal_Schedule schedule = new Personal_Schedule(1, "Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Work");

	    // Act
	    schedule.setSchedulename("Updated Meeting");

	    // Assert
	    assertEquals("Updated Meeting", schedule.getSchedulename());
	}
	
	@Test
	public void testScheduleTypeBranching() {
	    // Arrange
	    Personal_Schedule workSchedule = new Personal_Schedule(1, "Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Work");
	    Personal_Schedule personalSchedule = new Personal_Schedule(1, "Lunch", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Personal");

	    // Act
	    String workType = workSchedule.getType();
	    String personalType = personalSchedule.getType();

	    // Assert
	    assertEquals("Work", workType);
	    assertEquals("Personal", personalType);
	}
	
	@Test
	public void testSetEndTimeBranching() {
	    // Arrange
	    LocalDateTime startTime = LocalDateTime.now();
	    Personal_Schedule schedule = new Personal_Schedule(1, "Meeting", startTime, startTime.plusMinutes(30), "Work");

	    // Act
	    LocalDateTime newEndTime = startTime.plusMinutes(30);
	    schedule.setEndtime(newEndTime);

	    // Assert
	    assertEquals(startTime.plusMinutes(30), schedule.getEndtime());
	}
	
	@Test
	public void testMutateUserIDAssignment() {
	    // Arrange
	    Personal_Schedule schedule = new Personal_Schedule(1, "Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Work");

	    // Act
	    int mutatedUserID = schedule.getUserid() + 1;

	    // Assert
	    assertNotEquals(mutatedUserID, schedule.getUserid());
	}
	
	@Test
	public void testMutateScheduleType() {
	    // Arrange
	    Personal_Schedule schedule = new Personal_Schedule(1, "Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Work");

	    // Act
	    String mutatedType = "Leisure";
	    schedule.setType(mutatedType);

	    // Assert
	    assertEquals(mutatedType, schedule.getType());
	}

}
