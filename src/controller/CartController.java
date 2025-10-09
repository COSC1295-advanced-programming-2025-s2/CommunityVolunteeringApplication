package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cart;
import model.CartItem;
import model.DbUtil;
import model.Project;
import model.Session;

public class CartController implements SceneControlled{
	
	private SceneController sceneController;
	
	@FXML
	private TableView<CartItem> cartTable;

	//table columns 
	@FXML private TableColumn<CartItem, String> titleCol; @FXML private TableColumn<CartItem, String> locationCol; 
	@FXML private TableColumn<CartItem, String> dayCol; @FXML private TableColumn<CartItem, Integer> hoursCol; @FXML private TableColumn<CartItem, Integer> slotsCol;
	
	@FXML
	private Label msgLabel;
	
	
	
	@FXML
	public void confirm() throws IOException {
		if (Cart.getCart().isEmpty()) {
			msgLabel.setText("Cart is empty!");
		}
		else {
			sceneController.switchScene("/view/BookingMenu.fxml");
			


		}		
	}
	
	@FXML
	public void back() throws IOException {
		sceneController.switchScene("/view/dashboardMenu.fxml");
		
	}
	
	
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
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
