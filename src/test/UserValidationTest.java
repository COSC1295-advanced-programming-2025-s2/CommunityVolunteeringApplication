package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DbUtil;
import model.PasswordModel;
import model.SignupModel;
import model.User;

public class UserValidationTest {
	//--module-path "C:\Users\dashi\Downloads\zulu24.32.13-ca-fx-jdk24.0.2-win_x64\zulu24.32.13-ca-fx-jdk24.0.2-win_x64\lib" --add-modules javafx.controls,javafx.fxml
	//paste into run configurations if fx no class found error is popping up 
    
    @Test
    void testUserOrEmailAlreadyTaken() throws SQLException {
    	//this user exists already 
        String existingUsername = "maryj";
        String existingEmail = "mary@gmail.com";
        
        //this does not 
        String nonExistingUsername = "maryjane";
        String nonExistingEmail = "maryj@gmail.com";

        //existing username and email
        boolean result = DbUtil.userOrEmailIsTaken(existingUsername, existingEmail);
        assertTrue(result, "Existing username or email should return true");
        
        //existing username but new email
        boolean result2 = DbUtil.userOrEmailIsTaken(existingUsername, nonExistingEmail);
        assertTrue(result2, "Existing username or email should return true");
        
        //non existing username and email
        boolean result3 = DbUtil.userOrEmailIsTaken(nonExistingUsername, nonExistingEmail);
        assertFalse(result3, "Existing username or email should return false");

    }
    
    @Test
    void testLoginValidation() {
    	String username = "maryj";
    	String wrongUsername = "maryjj";
        String correctPassword = "marJ@403";
        String wrongPassword = "marJ@4033";

        User authTrue = DbUtil.userLogin(username, correctPassword);
        User authFalse = DbUtil.userLogin(username, wrongPassword);

        assertTrue(authTrue != null, "Correct password should return the user");
        assertTrue(authFalse == null, "Incorrect password should return null");
        assertTrue(DbUtil.userLogin(wrongUsername, correctPassword) == null, "Incorrect user should return null");
    }
    
    
    @Test
    void testPasswordSignupValidation() {    	
    	ArrayList<String> pCheck1 = PasswordModel.passwordIsValid("PASSWORD");
    	assertTrue(pCheck1.size() == 2, "Should show 2 : no number and no symbol " + pCheck1);

       	ArrayList<String> pCheck2 = PasswordModel.passwordIsValid("Password1");
    	assertTrue(pCheck2.size() == 1, "Should show 1 : no symbol " + pCheck2);
    	
    	ArrayList<String> pCheck3 = PasswordModel.passwordIsValid("Password1@");
    	assertTrue(pCheck3.isEmpty(), "Password should be allowed");
    	
    	ArrayList<String> pCheck4 = PasswordModel.passwordIsValid("p");
    	assertTrue(pCheck4.size() == 4, "Should show 4 : no symbol, less than 8 characters, no uppercase, no number " + pCheck4);
    }
    
    @Test
    void checkEmailValidation() {
    	
    	ArrayList<String> eCheck1 = SignupModel.emailIsValid("n");
    	assertTrue(eCheck1.size() == 2, "Should show 2 : no @ symbol, less than 3 characters " + eCheck1);
    	
    	ArrayList<String> eCheck2 = SignupModel.emailIsValid("n@");
    	assertTrue(eCheck2.size() == 1, "Should show 1 : less than 3 characters " + eCheck2);
    	
    	ArrayList<String> eCheck3 = SignupModel.emailIsValid("n@ss");
    	assertTrue(eCheck3.isEmpty(), "Should pass ");

    }
    
    @Test
    void fullNameValidation() {
    	ArrayList<String> nCheck1 = SignupModel.nameIsValid("n");
    	assertTrue(nCheck1.size() == 1, "Should show 1 : the user did not enter a space " + nCheck1);
    	
    	ArrayList<String> nCheck2 = SignupModel.nameIsValid("nick ming ying");
    	assertTrue(nCheck2.size() == 1, "Should show 1 : the user entered too many spaces " + nCheck2);
    	
    	ArrayList<String> nCheck3 = SignupModel.nameIsValid("ming ying");
    	assertTrue(nCheck3.isEmpty(), "Should be allowed " + nCheck3);
    	
    	ArrayList<String> nCheck4 = SignupModel.nameIsValid("nick-ming @ying");
    	assertTrue(nCheck4.size() == 1, "Should show 1: no special characters " + nCheck4);
    	
    	ArrayList<String> nCheck5 = SignupModel.nameIsValid("ni k");
    	assertTrue(nCheck5.size() == 1, "Should show 1: at least 2 characters in ur last name " + nCheck5);	
    	
    }
    
    
    
    


}
