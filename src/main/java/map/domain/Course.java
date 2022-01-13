package map.domain;

import java.util.List;
import java.util.Objects;

public class Course {
    long CourseId;

    private String CourseName;
    private long LehrerID;
    private int MaxNrofStudents;
    private int NrCredits;

    public Course(long id,String courseName,long lehrerid,
                  int maxNrofStudents,int nrCredits)
                   {

        this.CourseId=id;
        this.CourseName = courseName;
        this.LehrerID =lehrerid;

        this.MaxNrofStudents=maxNrofStudents;
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

    public long getLehrerID() {
        return LehrerID;
    }

    public void setLehrerID(long lehrerID) {
        LehrerID = lehrerID;
    }

    public int getMaxNrofStudents() {
        return MaxNrofStudents;
    }

    public void setMaxNrofStudents(int maxNrofStudents) {
        MaxNrofStudents = maxNrofStudents;
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
                ", courseId=" + CourseId + '\'' +
                ", maxEnrollment=" + MaxNrofStudents +
                ", credits=" + NrCredits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return CourseId == course.CourseId && MaxNrofStudents == course.MaxNrofStudents && NrCredits == course.NrCredits
                && Objects.equals(CourseName, course.CourseName) ;}

    @Override
    public int hashCode() {
        return Objects.hash(CourseId, CourseName, MaxNrofStudents, NrCredits);
    }



}
