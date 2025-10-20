package test;
import model.DbUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChangePasswordTest {
	

    @Test
    void testChangePasswordSuccess() throws Exception {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockSelect = mock(PreparedStatement.class);
        PreparedStatement mockUpdate = mock(PreparedStatement.class);
        ResultSet mockRS = mock(ResultSet.class);
        
        String oldp = "Marj3030@";
        String newp = "MAr@fl#5";

        try (MockedStatic<DbUtil> mockDb = mockStatic(DbUtil.class, CALLS_REAL_METHODS)) {
            mockDb.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement("SELECT password FROM users WHERE id = ?")).thenReturn(mockSelect);
            when(mockConn.prepareStatement("UPDATE users SET password = ? WHERE id = ?")).thenReturn(mockUpdate);

            when(mockSelect.executeQuery()).thenReturn(mockRS);
            when(mockRS.next()).thenReturn(true);
            when(mockRS.getString("password")).thenReturn("hash_old");
            
            //hashing and update
            mockDb.when(() -> DbUtil.hashPassword(oldp)).thenReturn("hash_old");
            mockDb.when(() -> DbUtil.hashPassword(newp)).thenReturn("hash_new");
            when(mockUpdate.executeUpdate()).thenReturn(1);
           
            //run the change password method (NOT REAL)
            boolean result = DbUtil.changePassword(oldp, newp, 1);

            assertTrue(result, "should be successfully changed when old password matches.");
            verify(mockUpdate).setString(1, "hash_new");
            verify(mockUpdate).setInt(2, 1);
        }
    }
}
