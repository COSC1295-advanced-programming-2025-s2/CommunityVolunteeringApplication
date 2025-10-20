package test;

import model.Project;
import java.util.*;

public class MockDbUtil {
    private static final Map<String, Project> mockProjects = new HashMap<>();

    private static String key(Project p) {
        return p.getTitle().toLowerCase() + "_" + p.getDay().toLowerCase();
    }

    // INSERT INTO projects with UNIQUE(title, day)
    public static boolean insertProject(Project project) {
        String k = key(project);
        if (mockProjects.containsKey(k)) {
            // simulate duplicate key violation
            return false;
        }
        mockProjects.put(k, project);
        return true;
    }

    // SELECT * FROM projects
    public static List<Project> getProjects() {
        return new ArrayList<>(mockProjects.values());
    }

    // Clear DB between tests
    public static void clear() {
        mockProjects.clear();
    }
}
