package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLPermission;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DbUtil {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
	private static final String DB_USER = "root";
    private static final String DB_PASS = "MegA!pAn3Ic";
    private static ObservableList<Project> projects = FXCollections.observableArrayList();
    private static ObservableList<Registration> registrations = FXCollections.observableArrayList();
    private static Set<Integer> projectIds = new HashSet<>();

    
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
//    public static void addProjectsFromCSV(String csv) {
//        String sql = """
//                INSERT INTO projects (title, location, day, hourly, registered_slots, total_slots, available_slots)
//                VALUES (?, ?, ?, ?, ?, ?, ?)
//                ON DUPLICATE KEY UPDATE location = VALUES(location), hourly = VALUES(hourly), registered_slots = VALUES(registered_slots), total_slots = VALUES(total_slots),
//                    available_slots = VALUES(available_slots)
//                """;
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             BufferedReader reader = new BufferedReader(new FileReader(csv))) {
//
//            String line;
//            boolean isFirstLine = true;
//
//            while ((line = reader.readLine()) != null) {
//                // Skip header if present
//                if (isFirstLine && line.toLowerCase().contains("title")) {
//                    isFirstLine = false;
//                    continue;
//                }
//
//                String[] parts = line.split(",");
//
//                if (parts.length < 6) {
//                    System.err.println("Skipping invalid line: " + line);
//                    continue;
//                }
//
//                for (int i = 0; i < parts.length; i++) {
//                    parts[i] = parts[i].trim();
//                }
//
//                stmt.setString(1, parts[0]); 
//                stmt.setString(2, parts[1]); 
//                stmt.setString(3, parts[2]); 
//                stmt.setInt(4, Integer.parseInt(parts[3])); 
//                stmt.setInt(5, Integer.parseInt(parts[4])); 
//                stmt.setInt(6, Integer.parseInt(parts[5])); 
//                stmt.setInt(7, Integer.parseInt(parts[5]) - Integer.parseInt(parts[4])); 
//                stmt.addBatch();
//            }
//
//            stmt.executeBatch();
//            System.out.println("Projects imported successfully from CSV");
//        }
//        catch (IOException e) {
//            System.err.println("Error reading CSV: " + e.getMessage());
//        }
//        catch (SQLException e) {
//            System.err.println("SQL error during import: " + e.getMessage());
//        }
//        catch (NumberFormatException e) {
//            System.err.println("Invalid number format: " + e.getMessage());
//        }
    //I've commented this out to show that I have imported all the data from the csv file into the database and therefore the csv file is redundant. 
    
    
    
    
    //imports the projects from the mysql database into a observable list 
    public static void importProjects(){
    	projects.clear();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM projects"); ){
        	while (rs.next()) {
        		projects.add(new Project(rs.getInt("projectId"), rs.getString("title"),rs.getString("location"),rs.getString("day"),rs.getInt("hourly"),rs.getInt("registered_slots"),rs.getInt("total_slots"),rs.getInt("available_slots"), rs.getInt("signupAvailable")));
        	}
        	
        	
        }
        catch (SQLException e) {
        	System.err.print(e);
        }
        
    }
    
    public static ObservableList<Project> getProjects(){
    	return projects;
    }
    
    public static ObservableList<Registration> getRegistrations(){
    	return registrations;
    }
    
    
    public static boolean projectRegistration(CartItem item) {
        try (Connection conn = getConnection()) {
        	
        	
        	//check if user has signed up for project
            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM registrations WHERE userId = ? AND projectId = ?")) {
                checkStmt.setInt(1, Session.getUser().getUserId());
                checkStmt.setInt(2, item.getProject().getProjectId());

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false; 
                        //prevents duplicate registration
                    }
                }
            }
            
            
            
            //insert registration
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO registrations (userId, projectId, hoursPerSlot, numberOfSlots, totalValue) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setInt(1, Session.getUser().getUserId());
                stmt.setInt(2, item.getProject().getProjectId());
                stmt.setInt(3, item.getHoursps());
                stmt.setInt(4, item.getNumps());
                stmt.setInt(5, item.getTotalValue());
                stmt.executeUpdate();
            }
            
            
            //update slots
            try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE projects SET available_slots = available_slots - 1, registered_slots = registered_slots + 1 WHERE projectId = ? AND available_slots > 0")) {
                updateStmt.setInt(1, item.getProject().getProjectId());
                int ru = updateStmt.executeUpdate();
                if (ru == 0) {
                    throw new IllegalStateException();
                }
            }
            
            //remove from users cart in the system .
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM cart WHERE userId = ? AND projectId = ?")) {
                stmt.setInt(1, Session.getUser().getUserId());
                stmt.setInt(2, item.getProject().getProjectId());
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Project registration failed: " + e.getMessage());
            return false;

        } catch (IllegalStateException e) {
            System.err.println("Registration failed due to lack of available slots for project ID: " + item.getProject().getProjectId());
            return false;
        }

     }
    
    public static void getAllRegistrations() {
    	registrations.clear();
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * from registrations")){
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {  
    			int projId = rs.getInt("projectId");
    			projectIds.add(projId);
    			Project pr = projects.stream().filter(p -> p.getProjectId() == projId).findFirst().orElse(null);
        		registrations.add(new Registration(pr,rs.getInt("registrationId"), rs.getString("confirmedAt"), rs.getInt("userId"), 
    					projId, rs.getInt("hoursPerSlot"), rs.getInt("numberOfSlots"),  rs.getInt("totalValue")));

            }
    		
    	}
    	
    	catch (SQLException e) {
    		System.err.println("An error occured: " + e);
    	}
    }
    
    public static void importRegistrations() {
    	registrations.clear();
    	String sql = """
    		    SELECT r.registrationId, r.confirmedAt, r.projectId, r.userId, p.title, p.location, p.day, r.numberOfSlots, r.hoursPerSlot, r.totalValue
    		    FROM registrations r JOIN projects p ON r.projectId = p.projectId
    		    WHERE r.userId = ? ORDER BY r.confirmedAt DESC
    		""";
    	
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
    		stmt.setInt(1, Session.getUser().getUserId());
    		ResultSet rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			int projId = rs.getInt("projectId");
    			projectIds.add(projId);
    			Project pr = projects.stream().filter(p -> p.getProjectId() == projId).findFirst().orElse(null);
    			
    			registrations.add(new Registration(pr, rs.getInt("registrationId"), rs.getString("confirmedAt"), rs.getInt("userId"), 
    					projId, rs.getInt("hoursPerSlot"), rs.getInt("numberOfSlots"),  rs.getInt("totalValue")));
    			}
    		
    		}
    	catch (SQLException e) {
    		System.err.println("An error occured when getting registrations: " + e);
    	}
    }
    
    //for mysql cart table
    public static boolean addToCart(int userId, CartItem item) throws SQLException {
        String sql = """
            INSERT INTO cart (userId, projectId, hoursPerSlot, numOfSlots, totalValue) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE hoursPerSlot = VALUES(hoursPerSlot),
            numOfSlots = VALUES(numOfSlots), totalValue = VALUES(totalValue) """;
        //if we want to update the project for example. 
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, item.getProject().getProjectId());
            stmt.setInt(3, item.getHoursps());
            stmt.setInt(4, item.getNumps());
            stmt.setInt(5, item.getTotalValue());
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean removeFromCart(int userId, int projectId) throws SQLException {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM cart WHERE user_id = ? AND project_id = ?")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, projectId);
            return stmt.executeUpdate() > 0;
        }
    }

    
    public static void getCartItems() throws SQLException {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT c.*, p.* FROM cart c JOIN projects p ON c.projectId = p.projectId WHERE c.userId = ?")) {
            stmt.setInt(1, Session.getUser().getUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	int projectId = rs.getInt("projectId");
                	
                	//finding matching projects based off of their project id, if not found then itll return null.
                	Project project = projects.stream().filter(p -> p.getProjectId() == projectId).findFirst().orElse(null);

                	if (project == null) {
                		//if theres no project we assume that an admin disabled signup or there are no more available slots so we delete it from the users cart. 
                		removeFromCart(Session.getUser().getUserId(), projectId);
                	    return;
                	}

                    CartItem item = new CartItem(project,
                        rs.getInt("hoursPerSlot"),
                        rs.getInt("numOfSlots"),
                        rs.getInt("totalValue")
                    );
                    
                    Cart.addItem(item);
                }
            }
        }
    }

    

    
    public static Set<Integer> getProjectIds(){
    	return projectIds;
    }
    
    public static boolean createProject(Project project) {
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("INSERT INTO projects (title, location, day, hourly, total_slots, registered_slots, available_slots) VALUES (?, ?, ?, ?, ?, ?, ?)")){
    		stmt.setString(1, project.getTitle());
    		stmt.setString(2, project.getLocation());
    		stmt.setString(3, project.getDay());
    		stmt.setInt(4, project.getHourly());
    		stmt.setInt(5, project.getTotalSlots());
    		stmt.setInt(6, project.getRegisteredSlots());
    		stmt.setInt(7, project.getAvailableSlots());
    		stmt.executeUpdate();
    		return true;


    	}
    	catch (SQLException e) {
            System.err.println("Project creation failed: " + e.getMessage());
            return false;

    	}
    }
    
    public static boolean enableOrDisableProject(Project project, boolean value) {
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("UPDATE projects SET signupAvailable = ? WHERE projectId = ?")){
    		for (Project p: projects) {
        		if (p.getTitle().equals(project.getTitle())) {
            		stmt.setBoolean(1, value);
            		stmt.setInt(2, p.getProjectId()); 
            		stmt.executeUpdate();
            		}

    		}
    		return true;
    	}
      	catch (SQLException e) {
    		System.err.println("Error:  " + e);

      		}
		return false;    		
    	}    
    
    
    
    public static boolean updateProject() throws SQLException {
    	Project project = Session.getProject();
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement("UPDATE projects SET title = ?, location = ?, hourly = ?, total_slots = ?, available_slots = ? WHERE projectId = ? ")) {
            stmt.setString(1, project.getTitle());
            stmt.setString(2, project.getLocation());
            stmt.setInt(3, project.getHourly());
            stmt.setInt(4, project.getTotalSlots());
            stmt.setInt(5, project.getAvailableSlots());
            stmt.setInt(6, project.getProjectId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
            	return true;
            	
            }
            
            return false;
            
        } 
        
        catch (SQLException e) {
            throw e;
        }
    }
    
    
    
    public static boolean updateProjectDays(List<String> selectedDays) throws SQLException {
    	Project selected = Session.getProject();
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); 
            // group all updates into one 

            // dlete unselected days for project
            if (!selectedDays.isEmpty()) {
                String placeholders = String.join(",", selectedDays.stream().map(d -> "?").toList());
                //builds placeholder list for the sql statement
                try (PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM projects WHERE title = ? AND day NOT IN (" + placeholders + ")")) {
                    deleteStmt.setString(1, selected.getTitle());
                    //i is 2 and not 1 because i alrdy used the first ? for the title. 
                    int i = 2;
                    for (String d : selectedDays) deleteStmt.setString(i++, d);
                    deleteStmt.executeUpdate();
                }
            }
            
       



            // insert for selected days
            try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO projects (title, location, hourly, total_slots, available_slots, day)  VALUES (?, ?, ?, ?, ?, ?)");
            		PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) from projects WHERE title = ? AND day = ?")) {
            	
            	//if the day is already a row we skip it
                for (String day : selectedDays) {
                    checkStmt.setString(1, selected.getTitle());
                    checkStmt.setString(2, day);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        rs.next();
                        int count = rs.getInt(1);
                        if (count > 0) {
                            continue;
                        }
                    }
                    
                    //otherwise we add it into our table. 
                    insertStmt.setString(1, selected.getTitle());
                    insertStmt.setString(2, selected.getLocation());
                    insertStmt.setInt(3, selected.getHourly());
                    insertStmt.setInt(4, selected.getTotalSlots());
                    insertStmt.setInt(5, selected.getAvailableSlots());
                    insertStmt.setString(6, day);
                    insertStmt.addBatch();

                }
                    
                insertStmt.executeBatch();
            }

            conn.commit();
            return true;
        } 
    }

 
    
    
    
    public static boolean registerUser(User user) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, fullname, email) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, user.getUsername().toLowerCase());
            stmt.setString(2, hashPassword(user.getPassword()));
            stmt.setString(3, user.getFullname());
            stmt.setString(4, user.getEmail().toLowerCase());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Registration failed: " + e.getMessage());
            return false;
        }
    }
    
    public static User userLogin(String username, String password) {
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT id, username, password, fullname, email FROM users WHERE username = ?")) {
    		stmt.setString(1, username.toLowerCase());
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
    	try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ? OR email = ?")) {
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
