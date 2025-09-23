package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
	private static final String DB_USER = "root";
    private static final String DB_PASS = "MegA!pAn3Ic";
    
    //connecting to the db
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    public static String hashPassword(String password) {
    	//WORK IN PROGRESS!!
    	return password;
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
    
    public static boolean userLogin(String username, String password) {
    	String sql = "SELECT password FROM users WHERE username = ?";
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			//will change this up when I implement hashing 
    			return rs.getString("password").equals(password);
    			
    		}
    		return false; //if user is not found
    	} 
    	catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
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
    
    


}
