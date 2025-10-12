package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import model.BookingModel;
import model.Cart;
import model.CartItem;
import model.DbUtil;

public class BookingController implements SceneControlled {
	private SceneController sceneController;
	
	@FXML
	private Label bookingLabel;
	
	@FXML
	private Button hiddenButton;
	//label containing the confirmation code
	@FXML
	private Label confirmationCode;
	
	@FXML
	private TextField codeTextField;
	
	@FXML
	private Text codeText;
	
	@FXML
	private ButtonBar buttonBar;
	
	@FXML
	private Label bookingSuccessfulLabel;
	private int confCode;
	
	
	@FXML
	private void verify() {
		//just because if someone enters nothing the system throws an error. 
		String txt = codeTextField.getText();
		if (txt.isEmpty()) {
			return;
		}
		int code = Integer.parseInt(txt);
		
		if (code == confCode) {
			CartItem c = Cart.checkout();
			
			if (c == null){
				
				bookingLabel.setText(BookingModel.getBookingConfirmation());
				bookingSuccessfulLabel.setText("Booking was successful!");
				//when booking is successful we clear the cart and import projects again to update the numbers + check if they've signed up for anything. 
				Cart.getCart().clear();
				DbUtil.importProjects(); 
				
				hiddenButton.setVisible(true);
				confirmationCode.setVisible(false);
				codeText.setVisible(false);
				codeTextField.setVisible(false);
				buttonBar.setVisible(false);
				
			}
			else {
				bookingLabel.setText("Registration for the project " + c.getProject().getTitle() + " on " + c.getProject().getDay() + " failed due to: user already signed up or no more available slots.");
				//this shouldnt be happening but I'll put it here just in case. 
			}

		}
		else {
			bookingSuccessfulLabel.setText("Invalid code, please try again.");
		}
		
		
		
		
	}
	@FXML
	private void backToHomepage() throws IOException {
		sceneController.switchScene("/view/dashboardMenu.fxml");
	}
	
	@FXML
	private void backToCart() throws IOException {
		sceneController.switchScene("/view/cartPage.fxml");
	}
	
	
	
	@FXML
	private void initialize() {
		confCode = BookingModel.generateCode();
		confirmationCode.setText("Your code is: " + confCode);
		
		//ensures that only numbers are entered 
		codeTextField.setTextFormatter(new TextFormatter<> (c -> c.getControlNewText().matches("\\d*") ? c : null));
	}
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	

}
