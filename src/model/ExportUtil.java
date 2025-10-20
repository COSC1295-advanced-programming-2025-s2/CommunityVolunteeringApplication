package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportUtil {
	
    public static boolean exportHistoryToFile(List<Registration> registrations, String username) {
        String filename = "history_" + Session.getUser().getUsername() + ".txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Participation History for " + username);
            writer.newLine();
            writer.write("=============================================");
            writer.newLine();
            writer.newLine();

            for (Registration r : registrations) {
                writer.write(String.format("Registration ID: %04d", r.getRegistrationId()));
                writer.newLine();
                writer.write("Confirmed at: " + (r.getDateTime()));
                writer.newLine();
                
                if (username.equals("admin")) {
                    writer.write("User ID: " + r.getUserId());
                    writer.newLine();
                    writer.write("Project ID: " + r.getProjectId());
                    writer.newLine();
                }
                
                writer.write("Project Title: " + r.getProject().getTitle());
                writer.newLine();
                writer.write("Location: " + r.getProject().getLocation());
                writer.newLine();
                writer.write("Day: " + r.getProject().getDay());
                writer.newLine();
                writer.write("Slots: " + r.getNumps());
                writer.newLine();
                writer.write("Hours per Slot: " + r.getHoursps());
                writer.newLine();
                writer.write("Total Value: $" + r.getTotalValue());
                writer.newLine();
                writer.write("--------------------------------------------");
                writer.newLine();
                writer.newLine();
            }
            
            return true;

            

        } catch (IOException e) {
            System.err.println("Error writing history file: " + e.getMessage());
            return false;
        }
    }


}
