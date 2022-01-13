package map.GUI;

import map.controller.CourseContr;
import map.controller.StudentContr;
import map.controller.TeacherContr;
import map.exceptions.InvalidCourseException;
import map.exceptions.NullValueException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import map.domain.Course;
import map.domain.Student;
import map.domain.Teacher;
import map.repo.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class TeacherGUI {

    private String firstName;
    private String lastName;

    public void setNames(String firstName, String lastname) {
        this.firstName = firstName;
        this.lastName = lastname;
    }


    ICrudRepo<Student> studentJdbcRepo;
    ICrudRepo<Teacher> teacherJdbcRepo;
    ICrudRepo<Course> courseJdbcRepo;

    IJoinTablesRepo enrolledJdbcRepo;
    private CourseContr courseController;
    private StudentContr studentController;
    private TeacherContr teacherController;


    VBox mainLayout;
    HBox hBoxButtons, hBoxListView;
    Button buttonShowStudents, buttonRefresh;
    ListView<String> listView;
    GridPane labels;
    Label labelCourseName;
    TextField courseNameInput;


    public Parent initialize() {
        studentJdbcRepo = new StudentJdbcRepo();
        teacherJdbcRepo = new TeacherJdbcRepo();
        courseJdbcRepo = new CourseJdbcRepo();

        enrolledJdbcRepo = new EnrolledJdbcRepo(studentJdbcRepo, courseJdbcRepo, teacherJdbcRepo);
        courseController = new CourseContr(courseJdbcRepo, teacherJdbcRepo, enrolledJdbcRepo);
        studentController = new StudentContr(studentJdbcRepo, courseJdbcRepo, enrolledJdbcRepo);
        teacherController = new TeacherContr(teacherJdbcRepo, enrolledJdbcRepo);


        listView = new ListView<>();
        listView.setPrefWidth(530);


        mainLayout = new VBox();
        mainLayout.setPrefWidth(300);
        mainLayout.setSpacing(70);
        mainLayout.setPadding(new Insets(0, 0, 0, 0));

        hBoxButtons = new HBox();
        hBoxButtons.setPrefWidth(300);
        hBoxButtons.setSpacing(48); //the spacing of the elements
        hBoxButtons.setPadding(new Insets(0, 0, 0, 0));


        hBoxListView = new HBox();
        hBoxListView.setPrefWidth(672);
        hBoxListView.setSpacing(15); //the spacing of the elements
        hBoxListView.setPadding(new Insets(0, 0, 0, 0));


        buttonShowStudents = new Button();
        buttonShowStudents.setText("See students enrolled");
        buttonShowStudents.setOnAction(e -> buttonShowStudentsClicked()); // Connect to action

        buttonRefresh = new Button();
        buttonRefresh.setText("Refresh");
        buttonRefresh.setOnAction(e -> buttonShowStudentsClicked());


        labels = new GridPane();
        labels.setPadding(new Insets(10, 10, 10, 10));
        labels.setVgap(8);
        labels.setHgap(15);

        labelCourseName = new Label("Course name:");
        courseNameInput = new TextField(); //create a blank input
        courseNameInput.setPromptText("course name");


        hBoxListView.getChildren().add(listView);
        hBoxButtons.getChildren().addAll(buttonShowStudents, buttonRefresh);
        labels.add(labelCourseName, 0, 0);
        labels.add(courseNameInput, 1, 0);


        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxListView.setAlignment(Pos.CENTER);
        labels.setAlignment(Pos.CENTER);
        mainLayout.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(hBoxButtons, labels, hBoxListView);
        return mainLayout;
    }


    private void buttonShowStudentsClicked() {
        try {
            listView.getItems().clear();
            Long courseId = courseController.searchCourse(courseNameInput.getText());
            Long teacherId = teacherController.searchPerson(firstName, lastName);

            if (!teacherController.verifyTeacherTeachesCourse(teacherId, courseId))
                throw new InvalidCourseException("Invalid course!");

            Course course = new Course(courseId, courseNameInput.getText(), -1, -1, -1);
            List<Long> studentIds = courseController.getStudentsEnrolledInCourse(course);

            if (studentIds.size() == 0)
                listView.getItems().add("No students enrolled!");
            else
                for (Long id : studentIds)
                    listView.getItems().add(studentController.findOne(id).toString());

            courseNameInput.clear();

        } catch (SQLException | IOException | ClassNotFoundException | NullValueException | InvalidCourseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Message");
            alert.setHeaderText("");
            alert.setContentText(e.toString());
            alert.showAndWait();
            courseNameInput.clear();
        }

    }
}
