import java.util.*;

public class Main {
    static class Course{ //indivitual coruse ko assine krne ke liye , taki Course re related cheejo ko manage krne mai asani ho
        String name; //Course ka naam
        List<Course> requirements; //Course ko krne ke liye requirement (means agr vo DSA ka Course krna chahta hai to usse pehle koi language Course padha hona chahiye)
        int credits;// kitna us Course ka credit hai
        public Course(String name,int credits) { //parameterised constructor
            this.name = name;
            this.requirements = new ArrayList<>();
            this.credits = credits;
        }

        public String getName(){ //Name return krvane ke liye
            return name;
        }

        public int getCredits(){ //Credits return krvane ke liye
            return credits;
        }

        public void addRequirement(Course requirement){ //Requirement(list) mai requirement dalne ke liye
            requirements.add(requirement);
        }

        public List<Course> getRequirements(){ //requirement return krne ke liye
            return requirements;
        }
    }

    static class Period{ 
        List<Course> courses;//Courses ki list is period ke liye
        int Totalcredits;//is period mai kitne credits hue
        public Period(){
            this.courses = new ArrayList<>();//list of Courses for this period
            this.Totalcredits = 0;//initializing credit value to zero
        }

        public void addCourse(Course course) { //courses(list) mai course add krna
            courses.add(course);
            Totalcredits += course.getCredits();//Pehle poorane credit ko lana phir increment krna
        }

        public List<Course> getCourses() {//courses ko return krvanee ke liye
            return courses;
        }

        public int getTotalCredits() { // Total credit return krvane ke liye
            return Totalcredits;
        }
    }

    static class ScheduleGenerator{
        List<Course> Courses;
        List<Period> periods;
        int a;
    }

    public static void main(String[] args) {
    }
}