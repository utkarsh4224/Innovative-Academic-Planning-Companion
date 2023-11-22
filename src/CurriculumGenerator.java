import java.io.*;
import java.util.*;

public class CurriculumGenerator {
    // Define the maximum number of academic periods and other constraints
    int maxPeriods = 10;           // Maximum number of academic periods
    int minCreditsPerPeriod = 12;  // Minimum   academic load per period
    int maxCreditsPerPeriod = 18;  // Maximum academic load per period
    int minCoursesPerPeriod = 3;   // Minimum number of courses per period
    int maxCoursesPerPeriod = 5;   // Maximum number of courses per period

    // Read course data from CSV file into a list of course objects
    List<Course> courses = readCourseDataFromFile("courses.csv");
    Map<String, Course> courseMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    List<String>[] curriculum = new List[maxPeriods];

    public CurriculumGenerator(List<Course> courses) {
        for (int i = 0; i < maxPeriods; i++) {
            curriculum[i] = new ArrayList<>();
        }
        this.courses = courses;

        // Build the courseMap for efficient course lookup
        for (Course course : courses) {
            courseMap.put(course.name, course);
        }
    }

    // Implement the readCourseDataFromFile function
    public static List<Course> readCourseDataFromFile(String filePath) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    try {
                        int credits = Integer.parseInt(parts[1].trim());
                        List<String> prerequisites = new ArrayList<>();
                        if (parts.length >= 3 && !parts[2].isEmpty()) {
                            String[] prereqArray = parts[2].trim().split(",");
                            for (String prereq : prereqArray) {
                                prerequisites.add(prereq.trim());
                            }
                        }
                        Course course = new Course(name, credits, prerequisites);
                        courses.add(course);
                    } catch (NumberFormatException e) {
                        // Handle non-numeric credits here (e.g., skip the line or log an error)
                        System.err.println("Skipping line due to non-numeric credits: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    // Implement the findCourseByName function using the courseMap
    Course findCourseByName(String courseName) {
        return courseMap.get(courseName);
    }

    // Implement the canAddCourse function
    boolean canAddCourse(int period, Course course) {
        // Calculate the total credits of courses in the current academic period
        int totalCredits = 0;

        for (String courseName : curriculum[period]) {
            Course c = findCourseByName(courseName);
            totalCredits += c.credits;
        }

        // Check if adding the new course will exceed the maximum credits per period
        // and if the maximum number of courses per period has not been reached
        return totalCredits + course.credits <= maxCreditsPerPeriod
                && curriculum[period].size() < maxCoursesPerPeriod;
    }

    // Implement the generateCurriculum function
    void generateCurriculum() {
        // Sort courses by their prerequisites (courses with no prerequisites first)
        courses.sort(Comparator.comparingInt(a -> a.prerequisites.size()));

        // Greedy course assignment
        for (Course course : courses) {
            boolean added = false;
            for (int period = 0; period < maxPeriods; period++) {
                if (canAddCourse(period, course)) {
                    curriculum[period].add(course.name);
                    added = true;
                    break;
                }
            }
        }

        // Ensure that each period meets the minimum academic load and course count
        for (int period = 0; period < maxPeriods; period++) {
            while (calculateTotalCredits(period) < minCreditsPerPeriod
                    || curriculum[period].size() < minCoursesPerPeriod) {
                // Try to move a course from a later period to this one
                boolean moved = false;
                for (int laterPeriod = period + 1; laterPeriod < maxPeriods; laterPeriod++) {
                    if (!curriculum[laterPeriod].isEmpty()) {
                        String courseToMove = curriculum[laterPeriod].remove(0);
                        Course movedCourse = findCourseByName(courseToMove);
                        curriculum[period].add(courseToMove);
                        moved = true;
                        break;
                    }
                }
                if (!moved) {
                    // If no course can be moved, break the loop (suboptimal solution)
                    break;
                }
            }
        }
    }

    // Implement the calculateTotalCredits function
    int calculateTotalCredits(int period) {
        int totalCredits = 0;

        // Iterate through the course names in the curriculum for the specified period
        for (String courseName : curriculum[period]) {
            // Find the Course object by name
            Course course = findCourseByName(courseName);

            // Add the credits of the course to the total
            totalCredits += course.credits;
        }

        return totalCredits;
    }

    // Implement the writeCurriculumToFile function
    void writeCurriculumToFile(List<String>[] curriculum, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);

            // Write header row
            writer.append("Period,Course Name\n");

            // Write course assignments for each period
            for (int period = 0; period < curriculum.length; period++) {
                for (String courseName : curriculum[period]) {
                    writer.append(String.valueOf(period + 1)).append(",").append(courseName).append("\n");
                }
            }
            writer.flush();
            writer.close();
            System.out.println("Curriculum written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing curriculum to file: " + e.getMessage());
        }
    }
}
