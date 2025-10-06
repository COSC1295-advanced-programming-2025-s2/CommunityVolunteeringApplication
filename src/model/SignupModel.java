package model;

import java.util.ArrayList;

public class SignupModel {
	
	
	public static ArrayList<String> usernameIsValid(String username){
		ArrayList<String> check = new ArrayList<>();
		
		if (underLen(username)) {
			check.add("be above or equal to 3 characters");
		}
		else if (username.length() > 20) {
			check.add("be below 20 characters");
		}
		
		if (username.matches(".*[^a-zA-Z0=9].*")) {
			check.add("have no special symbols or whitespaces");
		}
		
		return check;
	}
	
	public static ArrayList<String> nameIsValid(String name){
		ArrayList<String> check = new ArrayList<>();
		
		if (name.isEmpty()) {
			return check;
		}
		
		
		//split the name into 2 
		String[] fullName = name.split(" ");
		if (fullName.length == 1 || fullName.length > 2) {
			//if the user did not add a space between their first and last name
			check.add("your full name (make sure to have a space between your first and last name and dont include your middle name)");
		}
		
		else {
			if (fullName[0].length() < 2) {
				check.add("at least 2 characters in your first name");				
			}
			
			if (fullName[1].length() < 2) {
				check.add("at least 2 characters in your last name");				
			}
		}
		return check;
	}
	
	public static String registerUser(User user) {
		if (DbUtil.userOrEmailIsTaken(user.getUsername(), user.getEmail())) {
			return "Username or email is already taken.";
		}
		boolean success = DbUtil.registerUser(user);
		
		//condition ? value_if_true : value_if_false;
		return success ? null : "An error occurred whilst trying to register the user.";
	}
	
	public static ArrayList<String> emailIsValid(String email){
		ArrayList<String> check = new ArrayList<>();
		
		if (email.isBlank()) {
			return check;
		}
		
		if (underLen(email)){
			check.add("3 characters");
		}
		//if the email doesnt contain the @ symbol OR if the email contains multiple @ symbols (for char in email, if char == '@' then char count + 1) > 1
		if (!(email.contains("@")) || email.chars().filter(c -> c == '@').count() > 1) {
			check.add("only one @ symbol");
		}
		
		
		
		return check;
		
	}
	
	public static boolean underLen(String str) {
		if (str.length() > 0 && str.length() < 3) {
			return true;
		}
		return false;
	}
	
	public static boolean emptyField(String input) {
		if (input.isBlank()) {
			return true;
		}
		return false;
	}
	



}
