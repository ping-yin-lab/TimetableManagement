package pfy1.PingFungYin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class testSpecificationBased {

	@Test
    public void testAddAdmin() {
        adminDatabase ADdatabase = new adminDatabase();
        adminUser testUser = new adminUser("SPB123", "SPBtest", "SPBuser", "cloud", "SPBtu123", "password");
        ADdatabase.ADaddUser(testUser);

        Assert.assertTrue("Admin user should exist after addition", ADdatabase.userExists("SPBtu123", "SPB123"));
    }

    @Test
    public void testDeleteAdmin() {
        adminDatabase ADdatabase = new adminDatabase();
        adminUser testUser = new adminUser("SPB321", "SPBtest1", "SPBuser1", "cloud", "SPBtu321", "password");
        ADdatabase.ADaddUser(testUser);

        ADdatabase.deleteADuser("SPB321");
        Assert.assertFalse("Admin user should not exist after deletion", ADdatabase.userExists("SPBtu321", "SPB321"));
    }
    
    @Test
    public void testAddStudent() {
        studentDatabase STdatabase = new studentDatabase();
        studentUser testUser = new studentUser("SPB123", "SPBtest", "SPBuser", "cloud", "SPBtu123", "password");
        STdatabase.STaddUser(testUser);

        Assert.assertTrue("Student user should exist after addition", STdatabase.userExists("SPBtu123", "SPB123"));
    }

    @Test
    public void testDeleteStudent() {
        studentDatabase STdatabase = new studentDatabase();
        studentUser testUser = new studentUser("SPB321", "SPBtest1", "SPBuser1", "cloud", "SPBtu321", "password");
        STdatabase.STaddUser(testUser);

        STdatabase.deleteSTuser("SPB321");
        Assert.assertFalse("Sstudent user should not exist after deletion", STdatabase.userExists("SPBtu321", "SPB321"));
    }
    
    @Test
    public void testAddTeacher() {
        teacherDatabase TEdatabase = new teacherDatabase();
        teacherUser testUser = new teacherUser("SPB123", "SPBtest", "SPBuser", "cloud", "SPBtu123", "password");
        TEdatabase.TEaddUser(testUser);

        Assert.assertTrue("Student user should exist after addition", TEdatabase.userExists("SPBtu123", "SPB123"));
    }

    @Test
    public void testDeleteTeacher() {
    	teacherDatabase TEdatabase = new teacherDatabase();
    	teacherUser testUser = new teacherUser("SPB321", "SPBtest1", "SPBuser1", "cloud", "SPBtu321", "password");
        TEdatabase.TEaddUser(testUser);

        TEdatabase.deleteTEuser("SPB321");
        Assert.assertFalse("Sstudent user should not exist after deletion", TEdatabase.userExists("SPBtu321", "SPB321"));
    }
    
    @Test
    public void testAddLectureHall() {
    	lecturehallDatabase LHdatabase = new lecturehallDatabase();
    	LectureHall lecture = new LectureHall("SPB123", "SPBTest1", 100, 200 );
        LHdatabase.addLH(lecture);
        Assert.assertTrue("Student user should exist after addition", LHdatabase.lectureExists("SPB123"));
    }
    
    @Test
    public void testAddModule() {
    	moduleDatabase MDdatabase = new moduleDatabase();
    	Module mod = new Module("SPB123", "SPBtest","THis is a test for SPB testing", "SPBteacher" );
        MDdatabase.addModule(mod);
        Assert.assertTrue("Student user should exist after addition", MDdatabase.moduleExists("SPB123"));
    }
    
    @Test
    public void testAddAnnouncement() {
    	announcementDatabase ANdatabase = new announcementDatabase();
        Announcement ann = new Announcement("SPB Testing", "This is a test for SPB black box testing");
        ANdatabase.addAN(ann);
    }


}
