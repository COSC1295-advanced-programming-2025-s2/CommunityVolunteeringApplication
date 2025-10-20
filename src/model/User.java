package model;

public class User {
	private int id;
	private String username;
	private String fullname;
	private String password;
	private String email;
	private boolean admin;
	
	
	public User() {
		
	}
	public User(String fullname, String email, String username, String password) {
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.email = email;
		
		
	}
	
	public User(int id, String fullname, String email, String username, String password) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.email = email;
		
		
	}
	
	public User(int id, String fullname, String username, String email) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.email = email;
	}
	
	
	public void setAdmin(boolean value) {
		this.admin = value;
	}
	
	
	//getters
	public boolean isAdmin() {
		return admin;
	}
	
    
    public String getUsername() {
    	return username;
    }
    
    public String getFullname() {
    	return fullname;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public int getUserId() {
    	return id;
    }
    public void setUserId(int userId) {
    	id = userId;
    }


}
