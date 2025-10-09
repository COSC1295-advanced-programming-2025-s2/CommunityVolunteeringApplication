package model;

public class Registration extends CartItem {
	
	private String dateTime;
	private int registrationId;
	private int userId;
	private int projectId;
	
	
	
	public Registration() {
		
	}
	
	public Registration(Project project, int registrationId, String dateTime, int userId, int projectId, int hoursps, int numOfSlots, int totalValue) {
		super(project, hoursps,numOfSlots, totalValue);
		this.registrationId = registrationId;
		this.dateTime = dateTime;
		this.userId = userId;
		this.projectId = projectId;
		
		
	}
	//getters
	
	public String getDate() {
		return dateTime;
	}
	
	public int getRegistrationId() {
		return registrationId;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	
	
	
	@Override
	public String toString() {
		String id = String.format("Registration ID: %04d", registrationId);
		return id + " registered at " + dateTime + "\nUser ID : " + userId + "\nProject: "  + super.toString() +"\n--------------------------------------------";
	}

}
