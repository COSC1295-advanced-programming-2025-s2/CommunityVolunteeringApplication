package test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import model.Project;

public class MockDbUtilTest {
	
    @BeforeEach
    void setup() {
        MockDbUtil.clear(); 
        //reset before each test
    }

    @Test
    void testUniqueTitleDayConstraint() {
        Project p1 = new Project(1, "Beach Cleanup", "Beach", "Mon", 10, 0, 10, 10, 1);
        Project p2 = new Project(2, "Beach Cleanup", "Beach", "Tue", 10, 0, 10, 10, 1);
        Project p3 = new Project(3, "Park Cleanup", "Park", "Wed", 12, 0, 8, 8, 1);
        Project p4 = new Project(4, "Beach Cleanup", "Beach", "Mon", 10, 0, 10, 10, 1); 
        //duplicate key

        assertTrue(MockDbUtil.insertProject(p1), "first insert should succeed");
        assertTrue(MockDbUtil.insertProject(p2), "different day is allowed");
        assertTrue(MockDbUtil.insertProject(p3), "different title is allowed");
        assertFalse(MockDbUtil.insertProject(p4), "duplicate title+day should fail");

        assertEquals(3, MockDbUtil.getProjects().size(), "Should only store 3 unique entries");
    }

    @Test
    void testCaseInsensitiveConstraint() {
        Project p1 = new Project(1, "Tree Planting", "Park", "Sat", 10, 0, 10, 10, 1);
        Project p2 = new Project(2, "tree planting", "Park", "sat", 10, 0, 10, 10, 1);

        assertTrue(MockDbUtil.insertProject(p1));
        assertFalse(MockDbUtil.insertProject(p2), "same title/day but different case should be treated as duplicate");
    }

    @Test
    void testGetProjectsAfterInsert() {
        Project p1 = new Project(1, "Event A", "Hall", "Mon", 10, 0, 10, 10, 1);
        Project p2 = new Project(2, "Event B", "Hall", "Tue", 10, 0, 10, 10, 1);

        MockDbUtil.insertProject(p1);
        MockDbUtil.insertProject(p2);

        assertEquals(2, MockDbUtil.getProjects().size(), "Should return all inserted projects");
        assertTrue(MockDbUtil.getProjects().stream().anyMatch(p -> p.getTitle().equals("Event A")));
    }


}
