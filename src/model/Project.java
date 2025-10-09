package model;

import java.util.HashMap;

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
	
	public int getDayNum() {
		HashMap<String, Integer> days = new HashMap<>();
		days.put("Mon", 1); days.put("Tue", 2); days.put("Wed", 3); days.put("Thu", 4); days.put("Fri", 5); days.put("Sat", 6); days.put("Sun", 7);

		return days.get(day);
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
	
	public int getProjectId() {
		return projectId;
	}
	
	@Override
	public String toString() {
		return title + " at " + location + " on " + day + " ($" + hourly + " per hour)";
	}
	
	
	
	

}
