package model;

import java.util.Random;

public class BookingModel {
	
	
	public static int generateCode() {
		Random rand = new Random();
		String str = "";
		///maybe .. .make it so we have a string and we add random integers to that string and then we turn it back into a integer in the end. 
		for (int i = 0 ; i < 5 ; i ++) {
			int code = rand.nextInt(1,9);
			str += code;

		}
		
		return Integer.parseInt(str);
	}
	
	public static String getBookingConfirmation() {
		String str = "";
		str += "Cart items:\n";
		str += Cart.printCartItems();
		str += "\nParticipant: " + Session.getUser().getUsername();
		str =  str + "\nTotal contribution value: " + getContributionValue() + " (hourly value × hours × slots)";
		
		return str;
	}
	
	
	public static int getContributionValue() {
		int total = 0;
		for (CartItem c : Cart.getCart()) {
			total += c.getTotalValue();
			
		}
		return total;
	}
	

}
