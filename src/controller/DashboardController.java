package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cart;
import model.CartItem;
import model.DashboardModel;
import model.DbUtil;
import model.Project;
import model.Session;

public class DashboardController implements SceneControlled{
	
	private SceneController sceneController;
	private ObservableList<Project> projects = FXCollections.observableArrayList();		
	private Set<Integer> registeredProjectIds;
	
	
	@FXML
	private Label welcomeLabel;
	
	@FXML
	private TableView<Project> projectsTable;
	
	//table columns 
	@FXML private TableColumn<Project, String> titleCol; @FXML private TableColumn<Project, String> locationCol; 
	@FXML private TableColumn<Project, String> dayCol; @FXML private TableColumn<Project, Integer> hourlyCol; @FXML private TableColumn<Project, Integer> slotsCol;
	
	@FXML
	private Spinner<Integer> numOfHoursSpinner;
	@FXML
	private Spinner<Integer> numOfSlotsSpinner;
	
	@FXML
	private Label msgLabel;
	
	
	@FXML
	private void viewHistory() throws IOException {
		sceneController.switchScene("/view/registrationHistory.fxml");
	}
	
	@FXML
	private void changePassword() throws IOException {
		sceneController.switchScene("/view/changePasswordMenu.fxml");
	}
	
	
	@FXML
	private void logOut() throws IOException {
		DashboardModel.logout();
		sceneController.switchScene("/view/loginMenu.fxml");
	}
	
	
	@FXML
	private void add() throws SQLException {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		int hours = numOfHoursSpinner.getValue();
		int slots = numOfSlotsSpinner.getValue();
		
		//we assign a number corresponding to the day so monday = 1 , tuesday = 2, etc so if the number of the project day is lower than our current day, then we can assume it 
		//has alrdy passed. 
        DayOfWeek day = LocalDate.now().getDayOfWeek();
		if (selected.getDayNum() < day.getValue()) {
			msgLabel.setText("This event has already passed, please pick one in the future.");
		}
		//if item is already in cart then 
		//switched to equals by project id because it kept allowng duplicates and i didnt want to override the hash and equals function 
		else if (Cart.getCart().stream().anyMatch(item -> item.getProject().getProjectId() == selected.getProjectId())) {
			msgLabel.setText("Project is already in cart.");

			return;
																														
		}
		else {
			CartItem item = new CartItem(selected, hours, slots);
			Cart.getCart().add(item);
			DbUtil.addToCart(Session.getUser().getUserId(), item);
			msgLabel.setText(selected.getTitle() + " on " + selected.getDay() + " was added to cart!");
		}

	}
	
	@FXML 
	private void remove() {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			msgLabel.setText("Please select a project to remove.");
		}
		else if (Cart.getCart().stream().filter(item -> item.getProject().equals(selected)).findFirst().isPresent()) {
			Cart.removeItem(selected);
			try {
				DbUtil.removeFromCart(Session.getUser().getUserId(), selected.getProjectId());
				msgLabel.setText(selected.getTitle() + " on " + selected.getDay() +" was successfully removed from cart!");
			} catch (SQLException e) {
				msgLabel.setText("An error occurred when trying to remove the project from the cart:" + e);
				System.out.println(e);
				
			}
		}
		else {
			msgLabel.setText("Project was not found in cart.");
		}

		
		
	}
	@FXML
	private void viewCart() throws IOException {
		DbUtil.importRegistrations();
		sceneController.switchScene("/view/cartPage.fxml");
		
	}
	
	@FXML
	private void update() throws SQLException {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		int hours = numOfHoursSpinner.getValue();
		int slots = numOfSlotsSpinner.getValue();
		if (selected == null) {
			msgLabel.setText("Please select a project to update");
			
		}
		
		else if (Cart.getCart().stream().filter(item -> item.getProject().equals(selected)).findFirst().isPresent()) {
			CartItem item = Cart.updateItem(selected, hours, slots);
			if (item == null) {
				msgLabel.setText("Project was unable to be updated, please try again.");
				return;
			}
			DbUtil.addToCart(Session.getUser().getUserId(), item);
			msgLabel.setText(selected.getTitle() + " on " + selected.getDay() +" was successfully updated!");

		}
		
		else {
			msgLabel.setText("Project was not found in cart.");
		}
		
	}


	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	
	public void initialize() throws SQLException {
		DbUtil.importProjects(); 
		DbUtil.importRegistrations();
		DbUtil.getCartItems();
		welcomeLabel.setText("Welcome " + Session.getUser().getUsername() +"!");
		registeredProjectIds = DbUtil.getProjectIds();
		projects = DbUtil.getProjects();
		//gets the title variable from the Project class and sets the value on the column 
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
		hourlyCol.setCellValueFactory(new PropertyValueFactory<>("hourly"));
		slotsCol.setCellValueFactory(new PropertyValueFactory<>("availableSlots"));
		
		
		projectsTable.setItems(projects);
		
		//greys out any projects that the user has already signed up for.
		projectsTable.setRowFactory(tv -> new TableRow<Project>() {
		    @Override
		    protected void updateItem(Project project, boolean empty) {
		        super.updateItem(project, empty);
		        
		        if (project == null || empty) {
		            setStyle("");
		            setDisable(false);
		        //if the list of project ids contains the project id of a row it means theyve alrdy signed up for it so we grey it out (change the colour and disable selection)
		        }
		        else if (project.getSignupAvailable() == false) {
		        	projectsTable.getItems().remove(project);
		        }
		        else if (registeredProjectIds.contains(project.getProjectId())) {
		            setStyle("-fx-background-color: #d3d3d3;");
		            setDisable(true);
		        } 
		        else {
		            setStyle("");
		            setDisable(false);
		        }
		    }
		});



		
		//setting up the spinners, both start at 1 with 1 being the minimum and 3 the max. 
		SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
		SpinnerValueFactory<Integer> slotsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
		
		numOfHoursSpinner.setValueFactory(hoursFactory);
		numOfSlotsSpinner.setValueFactory(slotsFactory);





		
	}
	
	
	
	
	

}
