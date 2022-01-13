package map.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.controller.StudentContr;
import map.controller.TeacherContr;
import map.domain.Course;
import map.domain.Student;
import map.domain.Teacher;
import map.repo.*;

import java.io.IOException;
import java.sql.SQLException;


public class Login extends Application implements EventHandler<ActionEvent> {


    RadioButton radioButtonStudent, radioButtonTeacher;
    Button buttonSubmit;
    ToggleGroup answer;
    GridPane mainLayout, labels;
    HBox radios, hBoxSubmitButton;
    Label labelFirstName, labelLastName;
    TextField firstNameInput, lastNameInput;
    private final StudentContr studentController;
    private final TeacherContr teacherController;

    Parent mainParent;

    public void setParent(Parent parent) {

        this.mainParent = parent;

    }



    public Login() {
        ICrudRepo<Student> studentJdbcRepo = new StudentJdbcRepo();
        ICrudRepo<Teacher> teacherJdbcRepo = new TeacherJdbcRepo();
        ICrudRepo<Course> courseJdbcRepo = new CourseJdbcRepo();

        IJoinTablesRepo enrolledJdbcRepo = new EnrolledJdbcRepo(studentJdbcRepo, courseJdbcRepo, teacherJdbcRepo);
        this.studentController = new StudentContr(studentJdbcRepo, courseJdbcRepo, enrolledJdbcRepo);
        this.teacherController = new TeacherContr(teacherJdbcRepo, enrolledJdbcRepo);
    }

    public void launchGuiLogIn(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Log In");


        mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(10, 10, 10, 10)); //puts a ten pixel padding around your layout and the window
        mainLayout.setVgap(8); //the individual cells themselves they don't have any padding or spacing. All those cells are going to be bunched together
        mainLayout.setHgap(10);


        radios = new HBox();
        radios.setPrefWidth(300);
        radios.setSpacing(20); //the spacing of the elements
        radios.setPadding(new Insets(0, 0, 0, 0));


        hBoxSubmitButton = new HBox();
        hBoxSubmitButton.setPrefWidth(300);
        hBoxSubmitButton.setSpacing(20);
        hBoxSubmitButton.setPadding(new Insets(0, 0, 0, 0));


        labels = new GridPane();
        labels.setPadding(new Insets(10, 10, 10, 10));
        labels.setVgap(8);
        labels.setHgap(10);


        Label question = new Label("Student or teacher?");


        answer = new ToggleGroup();
        radioButtonStudent = new RadioButton("Student");
        radioButtonStudent.setToggleGroup(answer);
        radioButtonTeacher = new RadioButton("Teacher");
        radioButtonTeacher.setToggleGroup(answer);


        buttonSubmit = new Button();
        buttonSubmit.setText("Submit");
        buttonSubmit.setOnAction(this); // Connect to action


        labelFirstName = new Label("First name:");
        labelLastName = new Label("Last name:");
        labels.add(labelFirstName, 0, 0);
        labels.add(labelLastName, 0, 1);

        firstNameInput = new TextField(); //create a blank input
        firstNameInput.setPromptText("first name");
        lastNameInput = new TextField();
        lastNameInput.setPromptText("last name");
        labels.add(firstNameInput, 1, 0);
        labels.add(lastNameInput, 1, 1);


        radios.getChildren().addAll(radioButtonStudent, radioButtonTeacher, buttonSubmit);
        hBoxSubmitButton.getChildren().add(buttonSubmit);
        GridPane.setConstraints(question, 0, 0);
        GridPane.setConstraints(radios, 0, 1);
        GridPane.setConstraints(hBoxSubmitButton, 0, 6);
        GridPane.setConstraints(labels, 0, 5);


        radios.setAlignment(Pos.CENTER);
        hBoxSubmitButton.setAlignment(Pos.CENTER);
        labels.setAlignment(Pos.CENTER);


        mainLayout.getChildren().addAll(question, radios, labels, hBoxSubmitButton);
        Scene scene = new Scene(mainLayout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == buttonSubmit) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            Long exist;

            if (radioButtonStudent.isSelected()) {
                try {
                    exist = studentController.searchPerson(firstName, lastName);
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    alert.setTitle("Message");
                    alert.setHeaderText("");
                    alert.setContentText(e.toString());
                    alert.showAndWait();
                    return;
                }

                if (exist == null) {
                    firstNameInput.clear();
                    lastNameInput.clear();

                    alert.setTitle("Message");
                    alert.setHeaderText("");
                    alert.setContentText("You don't exist in the database!");
                    alert.showAndWait();
                } else {
                    StudentGUI guiStudent = new StudentGUI();
                    Parent parent = guiStudent.initialize();
                    guiStudent.setNames(firstName, lastName);
                    Stage stage = new Stage();
                    stage.setTitle("Student Menu");
                    stage.setScene(new Scene(parent, 672, 672));
                    stage.show();
                }
            } else {
                try {
                    exist = teacherController.searchPerson(firstName, lastName);
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    alert.setTitle("Message");
                    alert.setHeaderText("");
                    alert.setContentText(e.toString());
                    alert.showAndWait();
                    return;
                }

                if (exist == null) {
                    firstNameInput.clear();
                    lastNameInput.clear();

                    alert.setTitle("Message");
                    alert.setHeaderText("");
                    alert.setContentText("You don't exist in the database!");
                    alert.showAndWait();
                } else {
                    TeacherGUI guiTeacher = new TeacherGUI();
                    Parent parent = guiTeacher.initialize();
                    guiTeacher.setNames(firstName, lastName);
                    Stage stage = new Stage();
                    stage.setTitle("Teacher Menu");
                    stage.setScene(new Scene(parent, 672, 672));
                    stage.show();
                }
            }
        }
    }
}