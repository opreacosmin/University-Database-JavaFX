package map.domain;

import map.domain.Course;
import map.domain.Person;

import java.util.List;
import java.util.Objects;

public class Teacher extends Person {
    private List<Course> Courses;

    public Teacher(long id,String firstName, String lastName, List<Course> courses) {
        super(id,firstName, lastName);
        Courses = courses;
    }

    public List<Course> getCourses() {
        return Courses;
    }

    public void setCourses(List<Course> courses) {
        Courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                super.toString() +
                ", courses=" + Courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(Courses, teacher.Courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Courses);
    }

}
