public class TimeEvent{
	private Integer date;
	private String event;
	
	public TimeEvent(int date, String event) {
		this.date = date;
		this.event = event;
	}
	
	public int getDate() {
		return this.date;
	}
	
	public String getEvent() {
		return this.event;
	}

}
