package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectsDatabase {
	
	private static final ObservableList<Project> projects = FXCollections.observableArrayList();		
	
	
	public static ObservableList<Project> getList(){
		return projects;
	}

	public static void importData() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/data/projects.csv"))){
			br.readLine();
			//skips header
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				try {
					int[] numValues = Arrays.stream(values, 3, values.length).mapToInt(Integer::parseInt).toArray();		
					//creates an array called numValues from the values array starting from index 3 and ending at the length of the array then turns each of those strings into a double
					projects.add(new Project(values[0],values[1],values[2],numValues[0],numValues[1],numValues[2]));
					//maybe a more efficient way to do it out there but this is all I can do for now. 
				}
				
				catch (NumberFormatException e) {
					System.out.println("Skipping record: " + Arrays.toString(values));
					//if an exception occurs then we don't add it to our arraylist. 
				}
				
				
			}
		}
		catch (FileNotFoundException e) {
		    System.out.println("CSV file not found");
		}
		
	}
	
	
}


		




