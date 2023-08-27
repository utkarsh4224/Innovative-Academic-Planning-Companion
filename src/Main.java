import java.util.*;
public class Main {
    static class Course{
        String name;
        List<Course> requirement;
        int academicload;

        public Course(String name,int academicload) {
            this.name = name;
            this.requirement = new ArrayList<>();
            this.academicload = academicload;
        }
    }

    static class Period{
        List<Course> courses;
        int academicload;
        public Period(){
            this.courses = new ArrayList<>();
            this.academicload = 0;
        }
    }
    public static void main(String[] args) {
    }
}