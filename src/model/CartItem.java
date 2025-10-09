package model;

public class CartItem {
	protected Project project;
	protected int hoursps;
	//hours per slot
	protected int numOfSlots;
	//number of slots
	protected int totalValue;
	
	public CartItem() {
		
	}
	
	public CartItem(Project project, int hoursps, int numOfSlots) {
		this.project = project;
		this.hoursps = hoursps;
		this.numOfSlots = numOfSlots;
		totalValue = getTotalValue();
	}
	
	public CartItem(Project project, int hoursps, int numOfSlots, int totalValue) {
		this.project = project;
		this.hoursps = hoursps;
		this.numOfSlots = numOfSlots;
		this.totalValue = totalValue;
		
	}
	
	
	
	public void setHours(int hours) {
		this.hoursps = hours;
		
	}
	
	public void setSlots(int slots) {
		this.numOfSlots = slots;
	}
	//getters
	
	public Project getProject() {
		return project;
	}
	
	public int getHoursps() {
		return hoursps;
	}
	
	public int getNumps() {
		return numOfSlots;
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
	

	public int getTotalValue() {
		return project.getHourly()*hoursps*numOfSlots;
	}
	
	
	@Override
	public String toString() {
		return project.getTitle() + " on " + project.getDay() + " (" + numOfSlots + " slots) (" + hoursps + " hours per slot)"; 
	}

}
