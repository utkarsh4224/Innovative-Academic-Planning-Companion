import java.util.List;

public class Course {
    String name;
    int credits;
    List<String> prerequisites;

    Course(String name, int credits, List<String> prerequisites) {
        this.name = name;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }
}
