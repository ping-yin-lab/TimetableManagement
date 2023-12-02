package teacher_personalmgnt;
import java.time.LocalDateTime;

public class Personal_Schedule {
	private int id;
	public int Userid;
	public String schedulename;
	public LocalDateTime starttime;
	public LocalDateTime endtime;
	public String type;
	
	public Personal_Schedule(int userid,String name, LocalDateTime sttime, LocalDateTime edtime, String type) {
		this.Userid = userid;
		this.schedulename = name;
		this.starttime = sttime;
		this.endtime = edtime;
		this.type = type;
	}

	public int getUserid() {
		return Userid;
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