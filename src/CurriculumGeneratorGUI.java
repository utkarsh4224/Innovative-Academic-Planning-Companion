import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class CurriculumGeneratorGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Curriculum Generator");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // FileChooser for selecting the input CSV file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Choose File button
        Button chooseFileButton = new Button("Choose Input File");
        chooseFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                // Read the course data from the selected file
                List<Course> courses = CurriculumGenerator.readCourseDataFromFile(selectedFile.getPath());
                CurriculumGenerator generator = new CurriculumGenerator(courses);
                generator.generateCurriculum();
                generator.writeCurriculumToFile(generator.curriculum, "curriculum.csv");
                showAlert("Curriculum generated successfully!", Alert.AlertType.INFORMATION);
            }
        });

        GridPane.setConstraints(chooseFileButton, 0, 0);

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        GridPane.setConstraints(exitButton, 1, 0);

        grid.getChildren().addAll(chooseFileButton, exitButton);

        Scene scene = new Scene(grid, 300, 80);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Curriculum Generator");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
