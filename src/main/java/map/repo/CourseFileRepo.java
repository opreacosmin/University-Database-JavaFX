package map.repo;
import map.exceptions.NullValueException;
import map.domain.Course;

import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseFileRepo extends CourseRepo implements IFileRepo<Course>{

    private String fileName;

    public CourseFileRepo(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Course create(Course obj) throws NullValueException, IOException {
        Course course = super.create(obj);
        writeToFile();
        return course;
    }

    @Override
    public Course update(Course obj) throws NullValueException, IOException {
       Course course = super.update(obj);
       writeToFile();
        return course;
    }

    @Override
    public Course delete(Long id) throws NullValueException, IOException{
        Course course = super.delete(id);
        writeToFile();
        return course;

    }

    @Override
    public Connection openConnection() throws ClassNotFoundException, SQLException, IOException {
        return null;
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {

    }

    @Override
    public List<Course> readDataFromDatabase(Connection connection) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public int size() throws SQLException, ClassNotFoundException, IOException {
        return 0;
    }

    @Override
    public Long searchCourse(String courseName) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Integer getTotalCreditsOfStudent(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Long searchPerson(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void readFromFile() throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        boolean cont = true;
        while (cont) {
            try (ObjectInputStream input = new ObjectInputStream(fis)) {
                Course course = (Course) input.readObject();
                if (course != null) {
                    repoList.add(course);
                } else {
                    cont = false;
                }
            } catch (Exception e ) {
                System.out.println("null object");
            }
        }
    }

    @Override
    public void writeToFile() throws IOException {
       ObjectOutputStream writter = new ObjectOutputStream(new FileOutputStream(fileName));
       for (Course course : repoList) {
           /*  writter.write(String.valueOf("ID:"+course.getCourseId()+",Name:"+course.getCourseName()+",Credits:"
                   +course.getNrCredits()+",MaxStudents:"+course.getMaxNrofStudents()+",Teacher:"+course.getLehrerName()));
           writter.newLine();*/
           writter.writeObject(course);

       }
       writter.flush();
       writter.close();

    }
}
