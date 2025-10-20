package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import model.BookingModel;
import model.CartItem;
import model.DbUtil;
import model.Project;
import model.Session;
import model.User;

public class ProjectTest {
	
    private User user;
    private Project p1;
    private Project p2;
    private CartItem c1;
    private CartItem c2;

    @BeforeEach
    void setup() {
    	//	public User(int id, String fullname, String email, String username, String password) {
        user = new User(1,"Seth Rogan", "seth@gmail.com", "sethr", "sethR2030@");
        Session.setUser(user);
    }
    
    
    @Test
    void testConfirmationCodeGenerationAndValidation() {
        int code = BookingModel.generateCode();

        //check that its a 5 digit number
        assertTrue(code >= 10000 && code <= 99999, "code should be a 5-digit number.");

        
        int correctInput = code;
        int wrongInput = 12345;

        assertEquals(code, correctInput, "correct code.");
        assertNotEquals(code, wrongInput, "wrong code");
    }

}
