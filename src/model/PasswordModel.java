package model;

import java.util.ArrayList;

public class PasswordModel {
	
	public static ArrayList<String> passwordIsValid(String password) {
		ArrayList<String> check = new ArrayList<>();
		
		//basically checks if the string has any of the following characteristics, if not, it'll add a message to the check arraylist telling them what they need to add.
		
		//if password is under 8 characters
		if (password.length() < 8) {
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


}
