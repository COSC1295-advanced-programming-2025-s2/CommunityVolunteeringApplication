package model;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
	public static ObservableList<CartItem> cart = FXCollections.observableArrayList();
	
	
	public static void addItem(CartItem item) {
		cart.add(item);
	}
	
	public static void removeItem(Project project) {
		cart.removeIf(c -> c.getProject().equals(project));
	}
	
	public static void updateItem(Project project, int hours, int slots) {
		Iterator<CartItem> it = cart.iterator();
		while (it.hasNext()) {
			CartItem item = it.next();
			if (item.getProject().equals(project)) {
				item.setHours(hours);
				item.setSlots(slots);
				return;
			}
		}
	}
	
	
	public static ObservableList<CartItem> getCart() {
		return cart;
	}
	
	public static CartItem checkout() {
		for (CartItem c : cart) {
			if (!(DbUtil.projectRegistration(c))) {
				return c;
			}
		}
		return null;

	}
	

	
	public static String printCartItems() {
		String str = "";
		for (CartItem c : cart) {
			str += c + "\n";
		}
		
		return str;
	}
	
	
	
	


}

