package pfy1.PingFungYin;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class testStatementBased{

//These are statement based test cases for the tt_admin.java file
	
	@Test
	public void sbtestSTaddUser() {
		studentDatabase STdatabase = new studentDatabase();
	    studentUser student = new studentUser("ST7746", "STPing", "STYin", "STCloud", "STpfy1", "STabc123");
	    STdatabase.STaddUser(student);
	    assertTrue("User added successfully!", STdatabase.userExists("STpfy1", "ST7746"));
	}
	
	@Test
	public void sbtestSTUpdate() {
		studentDatabase STdatabase = new studentDatabase();
	    STdatabase.STUpdate("ST7746", "STPingedit", "", "", "", "STxyz123");	
	}
	
	@Test
	public void sbtestdeleteSTuser() {
		studentDatabase STdatabase = new studentDatabase();
	    STdatabase.deleteSTuser("ST7746");	
	}
	
	@Test
	public void sbtestTEaddUser() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    teacherUser student = new teacherUser("TE7746", "TEPing", "TEYin", "TECloud", "TEpfy1", "TEabc123");
	    TEdatabase.TEaddUser(student);
	    assertTrue("User added successfully!", TEdatabase.userExists("TEpfy1", "TE7746"));
	}
	
	@Test
	public void sbtestTEUpdate() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    TEdatabase.TEUpdate("TE7746", "TEPingedit", "", "", "", "TExyz123");	
	}
	
	@Test
	public void sbtestdeleteTEuser() {
		teacherDatabase TEdatabase = new teacherDatabase();
	    TEdatabase.deleteTEuser("TE7746");	
	}
	
	@Test
	public void sbtestADaddUser() {
		adminDatabase ADdatabase = new adminDatabase();
	    adminUser student = new adminUser("SB123", "ADPing", "ADYin", "ADCloud", "SBpfy1", "ADabc123");
	    ADdatabase.ADaddUser(student);
	    assertTrue("User added successfully!", ADdatabase.userExists("SBpfy1", "SB123"));
	}
	
	@Test
	public void sbtestADUpdate() {
		adminDatabase ADdatabase = new adminDatabase();
	    ADdatabase.ADUpdate("AD7746", "ADPingedit", "", "", "", "ADxyz123");	
	}
	
	@Test
	public void sbtestdeleteADuser() {
		adminDatabase ADdatabase = new adminDatabase();
	    ADdatabase.deleteADuser("AD7746");	
	}
	
	@Test
	public void sbtestaddLH() {
		lecturehallDatabase LHdatabase = new lecturehallDatabase();
		LectureHall lecture = new LectureHall("LH123", "LHTest", 100, 200 );
	    LHdatabase.addLH(lecture);
	    assertTrue("Lecture added successfully!", LHdatabase.lectureExists("LH123"));
	}
	
	@Test
	public void sbtestaddModule() {
		moduleDatabase MDdatabase = new moduleDatabase();
		Module mod = new Module("SB123", "MDtest" ,"MD this is for the unit test cases", "MDteacher" );
	    MDdatabase.addModule(mod);
	    assertTrue("Module added successfully!", MDdatabase.moduleExists("SB123"));
	}
	

}
