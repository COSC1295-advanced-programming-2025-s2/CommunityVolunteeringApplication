package controller;

import javafx.fxml.FXML;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cart;
import model.CartItem;
import model.DbUtil;
import model.Project;
import model.Session;

public class CartController {
	
	@FXML
	private TableView<CartItem> cartTable;

	//table columns 
	@FXML private TableColumn<CartItem, String> titleCol; @FXML private TableColumn<CartItem, String> locationCol; 
	@FXML private TableColumn<CartItem, String> dayCol; @FXML private TableColumn<CartItem, Integer> hoursCol; @FXML private TableColumn<CartItem, Integer> slotsCol;
	
	
	
	
	@FXML
	public void confirm() {
		
	}
	
	@FXML
	public void back() {
		
	}
	
	
	
	public void initialize() {
		//gets the title variable from the Project class and sets the value on the column 
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
		hoursCol.setCellValueFactory(new PropertyValueFactory<>("hoursps"));
		slotsCol.setCellValueFactory(new PropertyValueFactory<>("numps"));
		
		cartTable.setItems(Cart.getCart());
		
	}

	

}
