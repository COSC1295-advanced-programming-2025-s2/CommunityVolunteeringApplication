package model;

public class Project {
	private String title;
	private String location;
	private String day;
	private int hourly;
	private int registeredSlots;
	private int totalSlots;
	private int availableSlots;
	
	public Project(String title, String location, String day, int hourly, int registeredSlots, int totalSlots) {
		this.title = title;
		this.location = location;
		this.day = day;
		this.hourly = hourly;
		this.registeredSlots = registeredSlots;
		this.totalSlots = totalSlots;
		availableSlots = registeredSlots - totalSlots;
		
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getAvailableSlots() {
		return availableSlots;
	}
	
	
	
	

}
