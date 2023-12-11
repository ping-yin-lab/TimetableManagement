package pfy1.PingFungYin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

class testRandomBased {

	@Test
    public void testAddAdminRandom() {
        adminDatabase ADdatabase = new adminDatabase();
        Random random = new Random();

        // Generate random data
        String randomId = String.valueOf(random.nextInt(1000));
        String randomFName = "RandomFName" + random.nextInt(100);
        String randomLName = "RandomLName" + random.nextInt(100);
        String randomCourse = "RandomCourse" + random.nextInt(100);
        String randomUsername = "RandomUsername" + random.nextInt(100);
        String randomPassword = "RandomPassword" + random.nextInt(100);

        // Create and add a random admin user
        adminUser randomUser = new adminUser(randomId, randomFName, randomLName, randomCourse, randomUsername, randomPassword);
        ADdatabase.ADaddUser(randomUser);

        // Assertions to check if the user was added successfully
        Assert.assertTrue("User should exist after addition", ADdatabase.userExists(randomUsername, randomId));
	}
	
	@Test
    public void testAddStudentRandom() {
        studentDatabase STdatabase = new studentDatabase();
        Random random = new Random();

        String randomId = String.valueOf(random.nextInt(1000));
        String randomFName = "RandomFName" + random.nextInt(100);
        String randomLName = "RandomLName" + random.nextInt(100);
        String randomCourse = "RandomCourse" + random.nextInt(100);
        String randomUsername = "RandomUsername" + random.nextInt(100);
        String randomPassword = "RandomPassword" + random.nextInt(100);

        studentUser randomUser = new studentUser(randomId, randomFName, randomLName, randomCourse, randomUsername, randomPassword);
        STdatabase.STaddUser(randomUser);

        Assert.assertTrue("User should exist after addition", STdatabase.userExists(randomUsername, randomId));
	}
	
	@Test
    public void testAddTeacherRandom() {
        teacherDatabase TEdatabase = new teacherDatabase();
        Random random = new Random();

        String randomId = String.valueOf(random.nextInt(1000));
        String randomFName = "RandomFName" + random.nextInt(100);
        String randomLName = "RandomLName" + random.nextInt(100);
        String randomCourse = "RandomCourse" + random.nextInt(100);
        String randomUsername = "RandomUsername" + random.nextInt(100);
        String randomPassword = "RandomPassword" + random.nextInt(100);

        teacherUser randomUser = new teacherUser(randomId, randomFName, randomLName, randomCourse, randomUsername, randomPassword);
        TEdatabase.TEaddUser(randomUser);

        Assert.assertTrue("User should exist after addition", TEdatabase.userExists(randomUsername, randomId));
	}
	
	@Test
    public void testAddModuleRandom() {
        moduleDatabase MDdatabase = new moduleDatabase();
        Random random = new Random();

        String randomId = String.valueOf(random.nextInt(1000));
        String randomName = "RandomName" + random.nextInt(100);
        String randomDescription = "RandomDescription" + random.nextInt(100);
        String randomTeacher = "RandomTEacher" + random.nextInt(100);

        Module mod = new Module(randomId, randomName, randomDescription, randomTeacher);
        MDdatabase.addModule(mod);

        Assert.assertTrue("User should exist after addition", MDdatabase.moduleExists(randomId));
	}
	
	@Test
    public void testAddAnnouncementRandom() {
        announcementDatabase ANdatabase = new announcementDatabase();
        Random random = new Random();

        String randomTitle = "RandomTitle" + random.nextInt(100);
        String randomMessage = "RandomMessage" + random.nextInt(100);

        Announcement ann = new Announcement(randomTitle, randomMessage);
        ANdatabase.addAN(ann);
	}
	
	@Test
    public void testAddLectureHallRandom() {
		lecturehallDatabase LHdatabase = new lecturehallDatabase();
        Random random = new Random();

        String randomCode = String.valueOf(random.nextInt(1000));
        String randomBldg = "RandomBldg" + random.nextInt(100);
        int randomRoom = random.nextInt(100);
        int randomPeople = random.nextInt(100);

        LectureHall hall = new LectureHall(randomCode, randomBldg, randomRoom, randomPeople);
        LHdatabase.addLH(hall);

        Assert.assertTrue("User should exist after addition", LHdatabase.lectureExists(randomCode));
	}

}
