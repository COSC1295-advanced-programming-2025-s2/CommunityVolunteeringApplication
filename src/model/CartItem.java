package model;

public class CartItem {
	private Project project;
	private int hoursps;
	//hours per slot
	private int numps;
	//number of slots
	
	public CartItem() {
		
	}
	
	public CartItem(Project project, int hoursps, int numps) {
		this.project = project;
		this.hoursps = hoursps;
		this.numps = numps;
	}
	
	public Project getProject() {
		return project;
	}
	
	public int getHoursps() {
		return hoursps;
	}
	
	public int getNumps() {
		return numps;
	}
	
	public String getTitle() {
		return project.getTitle();
	}
	
	public String getDay() {
		return project.getDay();
	}
	
	public String getLocation() {
		return project.getLocation();
	}

}
