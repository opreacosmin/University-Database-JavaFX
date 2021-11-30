package map.repo;
import map.exceptions.NullValueException;
import map.domain.Course;

import java.io.*;
import java.io.IOException;

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
