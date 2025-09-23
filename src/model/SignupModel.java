package model;

import java.util.ArrayList;

public class SignupModel {
	
	public static ArrayList<String> passwordIsValid(String password) {
		ArrayList<String> check = new ArrayList<>();
		
		//basically checks if the string has any of the following characteristics, if not, it'll add a message to the check arraylist telling them what they need to add.
		
		//if password is under 8 characters
		if (password == null || password.length() < 8) {
			check.add("8 characters");		
		}
		
		//if password doesnt have any letters
		if (!(password.matches(".*[a-zA-Z].*"))) {
			check.add("one alphabetical character");
			
		}
		
		//if password has no numbers
		if (!(password.matches(".*\\d.*"))) {
			check.add("one number");
		}
		
		//if password has no capital letters
		if (!(password.matches(".*[A-Z].*"))) {
			check.add("one capital letter");
		}
		
		//if password has no special symbols but excludes white spaces or tabs. 
		if (!(password.matches(".*[^a-zA-Z0-9].*"))) {
			check.add("one special symbol (*,%,&,~, etc)");
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
