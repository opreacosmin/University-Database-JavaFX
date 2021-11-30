package map.repo;

import map.exceptions.NullValueException;
import map.domain.Student;

import java.io.*;

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

