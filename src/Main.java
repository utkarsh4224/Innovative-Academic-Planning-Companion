import java.util.*;
public class Main {
    static class Subject{ //indivitual coruse ko assine krne ke liye , taki Subject re related cheejo ko manage krne mai asani ho
        String name; //Subject ka naam
        List<Subject> requirement; //Subject ko krne ke liye requirement (means agr vo DSA ka Subject krna chahta hai to usse pehle koi language subject padha hona chahiye)
        int credits;// kitna us Subject ka credit hai
        public Subject(String name,int credits) { //parameterised constructor
            this.name = name;
            this.requirement = new ArrayList<>();
            this.credits = credits;
        }
    }

    static class Period{ 
        List<Subject> Subjects;//Subjects ki list is period ke liye
        int credits;//is period mai kitne credits hue
        public Period(){
            this.Subjects = new ArrayList<>();//list of subjects for this period
            this.credits = 0;//initializing credit value to zero
        }
    }

    static class ScheduleGenerator{
        List<Subject> Subjects;

    }

    public static void main(String[] args) {
    }
}