package map.controller;


import map.domain.Course;
import map.domain.Teacher;
import map.exceptions.InvalidCourseException;
import map.exceptions.InvalidStudentException;
import map.exceptions.InvalidTeacherException;
import map.exceptions.NullValueException;
import map.repo.ICrudRepo;
import map.repo.IJoinTablesRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CourseContr {
        private final ICrudRepo<Course> courseJdbcRepo;
        private final ICrudRepo<Teacher> teacherJdbcRepo;
        private final IJoinTablesRepo enrolledJdbcRepo;

        public CourseContr(ICrudRepo<Course> courseJdbcRepo, ICrudRepo<Teacher> teacherJdbcRepo, IJoinTablesRepo enrolledJdbcRepo) {
            this.courseJdbcRepo = courseJdbcRepo;
            this.teacherJdbcRepo = teacherJdbcRepo;
            this.enrolledJdbcRepo = enrolledJdbcRepo;
        }


        /**
         * sorts courses descending by the number of enrolled students
         *
         * @return sorted list of students
         */
        public List<Course> sortCoursesByStudentsEnrolled() throws SQLException, IOException, ClassNotFoundException {
            Connection connection = courseJdbcRepo.openConnection();

            List<Course> courses = courseJdbcRepo.readDataFromDatabase(connection).stream()
                    .sorted((course, otherCourse) -> {
                        try {
                            return enrolledJdbcRepo.getStudentsEnrolledInCourse(otherCourse).size() - enrolledJdbcRepo.getStudentsEnrolledInCourse(course).size();
                        } catch (SQLException | IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());

            courseJdbcRepo.closeConnection(connection);
            return courses;
        }


        /**
         * filters the courses with the specified number of credits
         * <p>
         * //* @param credits number of credits
         *
         * @return list of courses
         */
        public List<Course> filterCoursesWithSpecifiedCredits(int credits) throws SQLException, IOException, ClassNotFoundException {
            Connection connection = courseJdbcRepo.openConnection();

            List<Course> courses = courseJdbcRepo.readDataFromDatabase(connection).stream()
                    .filter(course -> course.getNrCredits() == credits)
                    .collect(Collectors.toList());

            courseJdbcRepo.closeConnection(connection);
            return courses;
        }


        public Course findOne(Long id) throws NullValueException, SQLException, IOException, ClassNotFoundException {
            return courseJdbcRepo.findOne(id);
        }


        public List<Course> getAll() throws SQLException, IOException, ClassNotFoundException {
            return courseJdbcRepo.getAll();
        }


        /**
         * saves the parameter object in repoList. Returns the result of method save in CourseRepository
         *
         * @param course to be saved
         * @return result of method save in CourseRepository
         * @throws NullValueException      if the parameter object is null
         * @throws IOException             if the file is invalid
         * @throws InvalidTeacherException course has a teacher who doesn't exist in teacherRepoList
         * @throws InvalidStudentException course has a student in studentList who doesn't exist in studentRepoList
         */
        public Course save(Course course) throws NullValueException, InvalidTeacherException, IOException, InvalidStudentException, SQLException, ClassNotFoundException {
            if (course == null)
                throw new NullValueException("Invalid entity");

            Long teacherId = course.getLehrerID();
            if (teacherId != null) {
                Teacher teacher = teacherJdbcRepo.findOne(teacherId);
                if (teacher == null)
                    throw new InvalidTeacherException("Invalid teacher");
            }

            return courseJdbcRepo.create(course);
        }


        /**
         * deletes the object with the parameter id from repoList. Returns the result of method delete in CourseRepository
         * <p>
         * //@param id of the object to be deleted
         *
         * @return result of method delete in CourseRepository
         * @throws IOException        if the file is invalid
         * @throws NullValueException if the parameter object is null
         */
        public Course delete(Long id) throws NullValueException, IOException, SQLException, ClassNotFoundException {
            if (id == null)
                throw new NullValueException("Invalid entity");

            Course result = courseJdbcRepo.findOne(id);
            if (result == null)
                return null;

            enrolledJdbcRepo.deleteEnrolledStudentsFromCourse(id);
            courseJdbcRepo.delete(id);
            return result;
        }


        public Course update(Course course) throws IOException, NullValueException, SQLException, ClassNotFoundException {
            return courseJdbcRepo.update(course);
        }

        public int size() throws SQLException, IOException, ClassNotFoundException {
            return courseJdbcRepo.size();
        }

        public List<Long> getStudentsEnrolledInCourse(Course course) throws SQLException, IOException, ClassNotFoundException {
            return enrolledJdbcRepo.getStudentsEnrolledInCourse(course);
        }

        public void registerTeacherToCourse(Long teacherId, Long courseId) throws SQLException, IOException, ClassNotFoundException, NullValueException, InvalidTeacherException, InvalidCourseException {
            enrolledJdbcRepo.registerTeacherToCourse(teacherId, courseId);
        }

        public void registerStudentToCourse(Long studentId, Long teacherID)throws SQLException, IOException, ClassNotFoundException, NullValueException, InvalidTeacherException, InvalidCourseException {

        }

            public Long searchCourse(String courseName) throws SQLException, IOException, ClassNotFoundException, InvalidCourseException {
            Long id = courseJdbcRepo.searchCourse(courseName);

            if (id == null)
                throw new InvalidCourseException("The course doesn't exist in the database!");

            return id;
        }
    }
