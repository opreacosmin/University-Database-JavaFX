package map.repo;

import map.exceptions.NullValueException;
import map.domain.Student;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FileStudentRepo extends PersonRepo<Student>  implements IFileRepo<Student>{
    private String fileName;

    public FileStudentRepo(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public Student create(Student obj) throws NullValueException, IOException {
        Student student = super.create(obj);
        writeToFile();
        return student;
    }

    @Override
    public Student update(Student obj) throws NullValueException, IOException {
        Student student = super.update(obj);
        writeToFile();
        return student;
    }

    @Override
    public Student delete(Long id) throws NullValueException, IOException{
        Student student = super.delete(id);
        writeToFile();
        return student;

    }

    @Override
    public Connection openConnection() throws ClassNotFoundException, SQLException, IOException {
        return null;
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {

    }

    @Override
    public List<Student> readDataFromDatabase(Connection connection) throws SQLException, IOException, ClassNotFoundException {
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
                Student student = (Student) input.readObject();
                if (student != null) {
                    repoList.add(student);
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
        for (Student student : repoList) {
            writter.writeObject(student);
        }
        writter.flush();
        writter.close();

    }

}

