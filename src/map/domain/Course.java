package map.domain;

import java.util.List;
import java.util.Objects;

public class Course {
    long CourseId;
    private Person teacher;
    private String CourseName;
    private String LehrerName;
    private int MaxNrofStudents;
    private List<Student> StudentsList;
    private int NrCredits;

    public Course(long id,String courseName,String lehrerName, Person teacher,
                  int maxNrofStudents,int nrCredits,
                  List<Student>studentsList) {

        this.CourseId=id;
        this.CourseName = courseName;
        this.LehrerName=lehrerName;
        this.teacher=teacher;
        this.MaxNrofStudents=maxNrofStudents;
        this.StudentsList=studentsList;
        this.NrCredits=nrCredits;

    }

    public long getCourseId() {
        return CourseId;
    }

    public void setCourseId(long courseId) {
        CourseId = courseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getLehrerName() {
        return LehrerName;
    }

    public void setLehrerName(String lehrerName) {
        LehrerName = lehrerName;
    }

    public int getMaxNrofStudents() {
        return MaxNrofStudents;
    }

    public void setMaxNrofStudents(int maxNrofStudents) {
        MaxNrofStudents = maxNrofStudents;
    }

    public List<Student> getStudentsList() {
        return StudentsList;
    }

    public void setStudentsList(List<Student> studentsList) {
        StudentsList = studentsList;
    }

    public int getNrCredits() {
        return NrCredits;
    }

    public void setNrCredits(int nrCredits) {
        NrCredits = nrCredits;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + CourseName + '\'' +
                ", courseId=" + CourseId +
                ", teacher=" + '\'' + teacher.getFirstName() + " " + teacher.getLastName() + '\'' +
                ", maxEnrollment=" + MaxNrofStudents +
                ", studentsEnrolled=" + StudentsList +
                ", credits=" + NrCredits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return CourseId == course.CourseId && MaxNrofStudents == course.MaxNrofStudents && NrCredits == course.NrCredits && Objects.equals(CourseName, course.CourseName) && Objects.equals(teacher, course.teacher) && Objects.equals(StudentsList, course.StudentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CourseId, CourseName, teacher, MaxNrofStudents, StudentsList, NrCredits);
    }



}
