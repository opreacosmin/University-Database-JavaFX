package map.controller;


import map.domain.Teacher;
import map.exceptions.InvalidCourseException;
import map.exceptions.NullValueException;
import map.repo.ICrudRepo;
import map.repo.IJoinTablesRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TeacherContr {

    private final ICrudRepo<Teacher> teacherJdbcRepo;
    private final IJoinTablesRepo enrolledJdbcRepo;

    public TeacherContr(ICrudRepo<Teacher> teacherJdbcRepo, IJoinTablesRepo enrolledJdbcRepo) {
        this.teacherJdbcRepo = teacherJdbcRepo;
        this.enrolledJdbcRepo = enrolledJdbcRepo;
    }

    public Teacher findOne(Long id) throws NullValueException, SQLException, IOException, ClassNotFoundException, NullValueException {
        return teacherJdbcRepo.findOne(id);
    }

    public List<Teacher> getAll() throws SQLException, IOException, ClassNotFoundException {
        return teacherJdbcRepo.getAll();
    }

    /**
     * saves the parameter object in repoList. Returns the result of method save in PersonRepository<T>
     *
     * @param teacher to be saved
     * @return result of method save in PersonRepository<T>
     * @throws NullValueException     if the parameter object is null
     * @throws IOException            if the file is invalid
     */
    public Teacher create(Teacher teacher) throws NullValueException, IOException, SQLException, ClassNotFoundException {
        if (teacher == null)
            throw new NullValueException("Invalid entity");

        return teacherJdbcRepo.create(teacher);
    }

    /**
     * deletes the object with the parameter id from repoList. Returns the result of method delete in PersonRepository<T>
     * <p>
     * //@param id of the object to be deleted
     *
     * @return result of method delete in PersonRepository<T>
     * @throws IOException        if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Teacher delete(Long id) throws IOException, NullValueException, SQLException, ClassNotFoundException {
        if (id == null)
            throw new NullValueException("Invalid entity");

        Teacher result = teacherJdbcRepo.findOne(id);
        if (result == null)
            return null;

        enrolledJdbcRepo.deleteTeacherFromCourse(id);
        teacherJdbcRepo.delete(id);
        return result;
    }

    public Teacher update(Teacher teacher) throws IOException, NullValueException, SQLException, ClassNotFoundException {
        return teacherJdbcRepo.update(teacher);
    }

    public int size() throws SQLException, IOException, ClassNotFoundException {
        return teacherJdbcRepo.size();
    }
    public Long searchPerson(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        return teacherJdbcRepo.searchPerson(firstName, lastName);
    }
    public boolean verifyTeacherTeachesCourse(Long teacherId, Long courseId) throws SQLException, IOException, ClassNotFoundException, NullValueException, InvalidCourseException, InvalidCourseException {
        return enrolledJdbcRepo.verifyTeacherTeachesCourse(teacherId, courseId);
    }

}