package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
	public static ObservableList<CartItem> cart = FXCollections.observableArrayList();
	
	
	public static void addItem(CartItem item) {
		cart.add(item);
	}
	
	
	public static ObservableList<CartItem> getCart() {
		return cart;
	}
	
	


}
