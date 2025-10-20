package model;

public class Session {
	private static User currentUser;
	private static Project project;
	
	public static void setUser(User user) {
		currentUser = user;
	}
	
	//this is only rlly used for updating project menu however when more features r added this will be useful. 
	public static void setSelectedProject(Project p) {
		project = p;
	}
	
	public static Project getProject() {
		return project;
	}
	
	public static User getUser() {
		return currentUser;
	}
	
	public static void clear() {
		currentUser = null;
	}

}
