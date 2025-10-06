package model;

public class Project {
	private String title;
	private String location;
	private String day;
	private int hourly;
	private int registeredSlots;
	private int totalSlots;
	private int availableSlots;
	private int projectId;
	
	public Project() {
		
	}
	
	public Project(String title, String location, String day, int hourly, int registeredSlots, int totalSlots) {
		this.title = title;
		this.location = location;
		this.day = day;
		this.hourly = hourly;
		this.registeredSlots = registeredSlots;
		this.totalSlots = totalSlots;
		availableSlots = totalSlots - registeredSlots;
		
		
	}
	public Project(int projectId, String title, String location, String day, int hourly, int registeredSlots, int totalSlots, int availableSlots) {
		this.projectId = projectId;
		this.title = title;
		this.location = location;
		this.day = day;
		this.hourly = hourly;
		this.registeredSlots = registeredSlots;
		this.totalSlots = totalSlots;
		this.availableSlots = availableSlots;
	}
		
	
	//getters
	
	public String getTitle() {
		return title;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getDay() {
		return day;
	}
	
	public int getHourly() {
		return hourly;
	}
	
	public int getRegisteredSlots() {
		return registeredSlots;
	}
	
	public int getTotalSlots() {
		return totalSlots;
	}

	
	public int getAvailableSlots() {
		return availableSlots;
	}
	
	@Override
	public String toString() {
		return title + "\n" + location + "\n" + day + "\n" + hourly + "\n";
	}
	
	
	
	

}
