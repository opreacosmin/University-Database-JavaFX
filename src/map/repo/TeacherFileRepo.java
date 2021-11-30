package map.repo;

import map.exceptions.NullValueException;
import map.domain.Teacher;

import java.io.*;

public class TeacherFileRepo extends PersonRepo<Teacher> implements IFileRepo<Teacher>{
    private String fileName;

    public TeacherFileRepo(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Teacher create(Teacher obj) throws NullValueException, IOException {
        Teacher teacher = super.create(obj);
        writeToFile();
        return teacher;
    }

    @Override
    public Teacher update(Teacher obj) throws NullValueException, IOException {
        Teacher teacher = super.update(obj);
        writeToFile();
        return teacher;
    }

    @Override
    public Teacher delete(Long id) throws NullValueException, IOException {
        Teacher teacher = super.delete(id);
        writeToFile();
        return teacher;
    }

    @Override
    public void readFromFile() throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        boolean cont = true;
        while (cont) {
            try (ObjectInputStream input = new ObjectInputStream(fis)) {
                Teacher teacher = (Teacher) input.readObject();
                if (teacher != null) {
                    repoList.add(teacher);
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
        for (Teacher teacher : repoList) {
            writter.writeObject(teacher);
        }
        writter.flush();
        writter.close();


    }
}
