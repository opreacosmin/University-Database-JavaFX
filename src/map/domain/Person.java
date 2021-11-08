package map.domain;

import java.util.Objects;

public class Person {
    protected long ID;
    private String FirstName,LastName;

    public Person(Long id,String firstName, String lastName) {
        this.ID=id;
        this.FirstName = firstName;
        this.LastName = lastName;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFirstName() {

        return FirstName;
    }

    public void setFirstName(String firstName) {

        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {

        LastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + ID +
                ", firstName='" + FirstName + '\'' +
                ", lastName='" + LastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return ID == person.ID && Objects.equals(FirstName, person.FirstName) && Objects.equals(LastName, person.LastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, FirstName, LastName);
    }


}
