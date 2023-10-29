import java.util.*;

public class Main {
    public static void main(String[] args) {
        CurriculumGenerator generator = new CurriculumGenerator();
        generator.generateCurriculum();
        generator.writeCurriculumToFile(generator.curriculum, "curriculum.csv");
    }
}