package map.controller;


import map.exceptions.InvalidStudentException;
import map.exceptions.InvalidTeacherException;
import map.exceptions.NullValueException;
import map.domain.Course;
import map.domain.Student;
import map.domain.Teacher;
import map.repo.CourseFileRepo;
import map.repo.FileStudentRepo;
import map.repo.TeacherFileRepo;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CourseContr {
    private CourseFileRepo courseFileRepo;
    private FileStudentRepo fileStudentRepo;
    private TeacherFileRepo teacherFileRepo;

    public CourseContr(CourseFileRepo courseFileRepo, FileStudentRepo fileStudentRepo, TeacherFileRepo teacherFileRepo) {
        this.courseFileRepo = courseFileRepo;
        this.fileStudentRepo = fileStudentRepo;
        this.teacherFileRepo = teacherFileRepo;
    }

    /**
     * sorts courses descending by the number of enrolled students
     *
     * @return sorted list of students
     */
    public List<Course> sortCoursesByNrOfStudentsEnrolled(){
        return courseFileRepo.getAll().stream().sorted((course, otherCourse) -> otherCourse.getMaxNrofStudents() - course.getMaxNrofStudents()).collect(Collectors.toList());
    }

    /**
     * filters the courses with the specified number of credits
     *
     * @param credits number of credits
     * @return list of courses
     */
    public List<Course> filterCoursesWithSpecifiedCredits(int credits) {
        return courseFileRepo.getAll().stream()
                .filter(course -> course.getNrCredits() == credits)
                .collect(Collectors.toList());
    }
    public Course findOne(Long id) throws NullValueException {
        return courseFileRepo.findOne(id);
    }
    public List<Course> getAll() {
        return courseFileRepo.getAll();
    }

    /**
     * saves the parameter object in repoList. Returns the result of method save in CourseRepository
     *
     * @param course to be saved
     * @return result of method save in CourseRepository
     * @throws NullValueException if the parameter object is null
     * @throws IOException if the file is invalid
     * @throws InvalidTeacherException course has a teacher who doesn't exist in teacherRepolist
     * @throws InvalidStudentException course has a student in studentList who doesn't exist in studentRepoList
     */
    public Course create(Course course) throws NullValueException, InvalidTeacherException, IOException, InvalidStudentException {
        if (course == null)
            throw new NullValueException("Invalid object");

        Long teacherID = course.getLehrerID();
        if (teacherID!=null)
        {
            Teacher teacher = teacherFileRepo.findOne(teacherID);
            if (teacher==null)
                throw new InvalidTeacherException("Teacher not found ");
            else {
                teacher.getCourses().add(course.getCourseId());
            }
        }
        List<Student> studentsEnrolled = course.getStudentsList();
        if(studentsEnrolled.size()==0){
            Course result = courseFileRepo.create(course);
            if (result == null){
                courseFileRepo.writeToFile();
                teacherFileRepo.writeToFile();
            }
            return result;
        }
        List<Student> allStudents=fileStudentRepo.getAll();

        for (Student student:studentsEnrolled){
            if (fileStudentRepo.findOne(student.getID())==null)
                throw new InvalidStudentException("Student not found");

        }
        Course result = courseFileRepo.create(course);
        if (result==null){
            for (Student student:studentsEnrolled){
                Student student1=fileStudentRepo.findOne(student.getID()) ;
                student1.getEnrolledCourses().add(course);
            }
            courseFileRepo.writeToFile();
            fileStudentRepo.writeToFile();
            teacherFileRepo.writeToFile();

        }
        return result;
    }

    /**
     * updates the object with the new course type object. Returns the updated course
     *
     * @param course of the object to be updates
     * @return result of method update in CourseRepository
     * @throws IOException if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Course update(Course course) throws IOException, NullValueException {
        return courseFileRepo.update(course);
    }


    /**
     * deletes the object with the parameter id from repoList. Returns the result of method delete in CourseRepository
     *
     * @param id of the object to be deleted
     * @return result of method delete in CourseRepository
     * @throws IOException if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Course delete(Long id) throws IOException,NullValueException{
        if(id==null)
            throw new NullValueException("Invalid ID");
        Course result = courseFileRepo.delete(id);
        if (result==null)
            return  result;
        List<Teacher> allTeachers = teacherFileRepo.getAll();
        for (Teacher teacher:allTeachers){
            List<Long> courses = teacher.getCourses();
            courses.remove(id);
        }
        List<Student> allStudents = fileStudentRepo.getAll();
        for (Student student : allStudents){
            List<Course> enrolledCourses = student.getEnrolledCourses();
            enrolledCourses.remove(id);
        }
        fileStudentRepo.writeToFile();
        courseFileRepo.writeToFile();
        teacherFileRepo.writeToFile();
        return result;
    }
}
