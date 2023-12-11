package pfy1.PingFungYin;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class testBranchBased {
	
	@Test
	public void bbtestSTaddUserfail() {
		studentDatabase STdatabase = new studentDatabase();
	    studentUser student = new studentUser("ST902", "Abc", "xyz", "Cloud", "stabc", "pass");
	    STdatabase.STaddUser(student);
	}
	
	@Test
	public void bbtestSTaddUserpass() {
		studentDatabase STdatabase = new studentDatabase();
	    studentUser student = new studentUser("ST77465", "STPing", "STYin", "STCloud", "STpfy11", "STabc123");
	    STdatabase.STaddUser(student);
	    assertTrue("User added successfully!", STdatabase.userExists("STpfy11", "ST77465"));
	}
	
	@Test
	public void bbtestSTUpdatefail() {
		studentDatabase STdatabase = new studentDatabase();
	    STdatabase.STUpdate("", "", "", "", "", "");	
	}
	
	@Test
	public void bbtestSTUpdatepass() {
		studentDatabase STdatabase = new studentDatabase();
	    STdatabase.STUpdate("ST111", "STPingBB", "STYin", "STCloud", "STpfy1", "STabc123");	
	}
	
	@Test
	public void bbtestdeleteSTuserpass() {
		studentDatabase STdatabase = new studentDatabase();
	    STdatabase.deleteSTuser("ST111");	
	}
	
	@Test
	public void bbtestdeleteSTuserfail() {
		studentDatabase STdatabase = new studentDatabase();
	    STdatabase.deleteSTuser("ST111fail");	
	}
	
	@Test
	public void bbtestTEaddUserfail() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    teacherUser teacher = new teacherUser("TE345", "Abc", "xyz", "Cloud", "stabc", "pass");
	    TEdatabase.TEaddUser(teacher);
	}
	
	@Test
	public void bbtestTEaddUserpass() {
		teacherDatabase TEdatabase = new teacherDatabase();
		teacherUser teacher = new teacherUser("TE77461", "TEPing", "TEYin", "TECloud", "TEpfy11", "TEabc123");
	    TEdatabase.TEaddUser(teacher);
	    assertTrue("User added successfully!", TEdatabase.userExists("TEpfy11", "TE77461"));
	}
	
	@Test
	public void bbtestTEUpdatefail() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    TEdatabase.TEUpdate("", "", "", "", "", "");	
	}
	
	@Test
	public void bbtestTEUpdatepass() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    TEdatabase.TEUpdate("TE77461", "TEPingBB", "TEYin", "TECloud", "TEpfy11", "TEabc123");	
	}
	
	@Test
	public void bbtestdeleteTEuserpass() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    TEdatabase.deleteTEuser("TE77461");	
	}
	
	@Test
	public void bbtestdeleteTEuserfail() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    TEdatabase.deleteTEuser("TEfail774612");	
	}
	
	@Test
	public void bbtestADaddUserfail() {
		adminDatabase ADdatabase = new adminDatabase();
	    adminUser admin = new adminUser("TA123", "Abc", "xyz", "Cloud", "stabc", "pass");
	    ADdatabase.ADaddUser(admin);
	}
	
	@Test
	public void bbtestADaddUserpass() {
		adminDatabase ADdatabase = new adminDatabase();
		adminUser admin = new adminUser("AD77461", "ADPing", "ADYin", "ADCloud", "ADpfy11", "ADabc123");
		ADdatabase.ADaddUser(admin);
	    assertTrue("User added successfully!", ADdatabase.userExists("ADpfy11", "AD77461"));
	}
	
	@Test
	public void bbtestADUpdatefail() {
		adminDatabase ADdatabase = new adminDatabase();
	    ADdatabase.ADUpdate("", "", "", "", "", "");	
	}
	
	@Test
	public void bbtestADUpdatepass() {
		adminDatabase ADdatabase = new adminDatabase();
	    ADdatabase.ADUpdate("AD7746", "ADPingBB", "ADYin", "ADCloud", "ADpfy1", "ADabc123");	
	}
	
	@Test
	public void bbtestdeleteADuserpass() {
		adminDatabase ADdatabase = new adminDatabase();
	    ADdatabase.deleteADuser("TE7746");	
	}
	
	@Test
	public void bbtestdeleteADuserfail() {
		adminDatabase ADdatabase = new adminDatabase();
	    ADdatabase.deleteADuser("TEfail7746");	
	}
	
	@Test
	public void sbtestaddLHpass() {
		lecturehallDatabase LHdatabase = new lecturehallDatabase();
		LectureHall lecture = new LectureHall("LH1231", "LHTest", 100, 200 );
	    LHdatabase.addLH(lecture);
	    assertTrue("Class added successfully!", LHdatabase.lectureExists("LH123"));
	}
	
	@Test
	public void sbtestaddLHfail() {
		lecturehallDatabase LHdatabase = new lecturehallDatabase();
		LectureHall lecture = new LectureHall("A1", "LHTest", 100, 200 );
	    LHdatabase.addLH(lecture);
	}
	
	@Test
	public void bbtestaddModulepass() {
		moduleDatabase MDdatabase = new moduleDatabase();
		Module mod = new Module("MD3451", "MDtest","MD this is for the unit test cases", "MDteacher" );
	    MDdatabase.addModule(mod);
	    assertTrue("Module added successfully!", MDdatabase.moduleExists("MD3451"));
	}
	
	@Test
	public void bbtestaddModulefail() {
		moduleDatabase MDdatabase = new moduleDatabase();
		Module mod = new Module("CO1234", "MDtest","MD this is for the unit test cases", "MDteacher" );
	    MDdatabase.addModule(mod);
	}
	
	@Test
	public void bbtestaddAnnouncement() {
		announcementDatabase ANdatabase = new announcementDatabase();
		Announcement announce = new Announcement("This is from junit", "This is a message posted for junit testing");
	    ANdatabase.addAN(announce);
	}
}
