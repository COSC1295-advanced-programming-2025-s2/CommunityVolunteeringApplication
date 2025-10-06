package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLPermission;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DbUtil {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
	private static final String DB_USER = "root";
    private static final String DB_PASS = "MegA!pAn3Ic";
    private static ObservableList<Project> projects = FXCollections.observableArrayList();
    
    //connecting to the db
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    public static String hashPassword(String password) {
    	try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //performing the hash
            byte[] hash = digest.digest(password.getBytes());
            
            //convert the byte into a hexadecimal string
            StringBuilder hexStr = new StringBuilder();
            for (byte b : hash) {
            	//makes sure byte is treated as an unsigned value 
            	String hex = Integer.toHexString(0xff & b);
            	//if the result len is 1 then we add a 0 to make sure theres 2 digits. 
            	if (hex.length() == 1) {
            		hexStr.append('0');
            	}
            	hexStr.append(hex);
            }
            return hexStr.toString();
    	}
    	catch (NoSuchAlgorithmException e) {
    		throw new RuntimeException (e);
    	}
    	
    }
    
//    public static void addProjects (ObservableList<Project> projects) {
//    	String sql = "INSERT INTO projects (title, location, day, hourly, registered_slots, total_slots, available_slots) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
//    		for (Project p : projects) {
//    			stmt.setString(1, p.getTitle());
//    			stmt.setString(2, p.getLocation());
//    			stmt.setString(3, p.getDay());
//    			stmt.setInt(4, p.getHourly());
//    			stmt.setInt(5, p.getRegisteredSlots());
//    			stmt.setInt(6, p.getTotalSlots());
//    			stmt.setInt(7, p.getAvailableSlots());
//    			stmt.addBatch();
//    		}
//    		stmt.executeBatch();
//    		System.out.println("Successfully imported!");
//    		conn.close();
//    	}
//    	catch (SQLException e) {
//    		System.err.println("Error occured: " + e);
//    	}
//    	
//    	
//    }
    
    //I've commented this out to show that I have imported all the data from the csv file into the database and therefore the csv file is redundant. 
    
    //imports the projects from the mysql database into a observable list 
    public static void importProjects(){
        String sql = "SELECT * FROM projects";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); ){
        	while (rs.next()) {
        		projects.add(new Project(rs.getInt("projectId"), rs.getString("title"),rs.getString("location"),rs.getString("day"),rs.getInt("hourly"),rs.getInt("total_slots"),rs.getInt("registered_slots"),rs.getInt("available_slots")));
        	}
        	
        	
        }
        catch (SQLException e) {
        	System.err.print(e);
        }
        
    }
    
    public static ObservableList<Project> getProjects(){
    	return projects;
    }
    
    
    public static boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            //right now hashpassword does nothing but i'll change that when I implement it. 
            stmt.setString(2, hashPassword(user.getPassword()));
            stmt.setString(3, user.getFullname());
            stmt.setString(4, user.getEmail());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Registration failed: " + e.getMessage());
            return false;
        }
    }
    
    public static User userLogin(String username, String password) {
    	String sql = "SELECT id, username, password, fullname, email FROM users WHERE username = ?";
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			if (rs.getString("password").equals(hashPassword(password))){
    				User user = new User(rs.getInt("id"),rs.getString("fullName"), rs.getString("username"),rs.getString("email"));
    				return user;
    			}
    			
    		}
    		return null; //if user is not found
    	} 
    	catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
            return null;
    	}
    	
    }
    
    public static boolean userOrEmailIsTaken(String username, String email) {
    	String sql = "SELECT id FROM users WHERE username = ? OR email = ?";
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, username);
    		stmt.setString(2, email);
    		ResultSet rs = stmt.executeQuery();
    		return rs.next();
    	} 
    	catch (SQLException e) {
    		//System.err.print("Check failed: " + e.getMessage());
    		//if error, then username or email is probably taken so we return true
    		return true;
    	}
    }
    
    public static boolean changePassword(String oldPass, String newPass, int userId) {
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE id = ?")){
    		stmt.setInt(1, userId);
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    			if (!(hashPassword(oldPass).equals(rs.getString("password")))) {
    				return false;
        			
        		}
    			
    			try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?")){
    				updateStmt.setString(1, hashPassword(newPass));
    				updateStmt.setInt(2, userId);
    				int ra = updateStmt.executeUpdate();
    				//rows affected
    				return ra > 0;
    				
    			}
    			
    			
    		}

    	}
    	catch (SQLException e) {
    		System.err.print(e);
    	}
    	
    	return false;
    	
    }
    
    
    
    


}
