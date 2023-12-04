package student_mgmt;

import java.time.LocalDateTime;

public class Student_Schedule {
	private int id;
	public int studentId;
	public String schedulename;
	public LocalDateTime starttime;
	public LocalDateTime endtime;
	public String type;
	
	public Student_Schedule(int studentId,String name, LocalDateTime sttime, LocalDateTime edtime, String type) {
		this.studentId = studentId;
		this.schedulename = name;
		this.starttime = sttime;
		this.endtime = edtime;
		this.type = type;
	}

	public int getUserid() {
		return studentId;
	}
	public String getSchedulename() {
		return schedulename;
	}

	public void setSchedulename(String schedulename) {
		this.schedulename = schedulename;
	}

	public LocalDateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalDateTime starttime) {
		this.starttime = starttime;
	}

	public LocalDateTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalDateTime endtime) {
		this.endtime = endtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}