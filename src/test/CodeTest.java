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

public class CodeTest {

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
