package map.repo;
import map.exceptions.NullValueException;
import map.domain.Person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository Class that manages all CRUD operations for a Person object
 *
 * @param <T> generic PersonRepository
 */
public class PersonRepo<T extends Person> extends InMemoryRepo<T> {

    public PersonRepo() {
        super();
    }
    @Override
    public T findOne(Long id) throws NullValueException {
        if (id == null)
            throw new NullValueException("Invalid ID");
        for (Person person : repoList) {
            if (person.getID() == id)
                return (T) person;
        }
        return null;
    }
    @Override
    public T create(T obj) throws NullValueException, IOException {
        if (obj == null)
            throw new NullValueException("Invalid person object");
        for (Person person : repoList)
            if (person.getID() == obj.getID())
                return obj;
        repoList.add(obj);
        return null;
    }

    @Override
    public T update(T obj) throws  NullValueException, IOException {
        if (obj == null)
            throw new NullValueException("Invalid person object");
        for (Person person : repoList)
            if (person.getID() == obj.getID()) {
                repoList.remove(person);
                repoList.add(obj);
                return null;
            }
        return obj;
    }

    @Override
    public T delete(Long id) throws NullValueException, IOException {
        if (id == null)
            throw new NullValueException("Invalid person object");
        for (Person person : repoList)
            if (person.getID()== id) {
                repoList.remove(person);
                return (T) person;
            }
        return null;
    }

    @Override
    public Connection openConnection() throws ClassNotFoundException, SQLException, IOException {
        return null;
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {

    }

    @Override
    public List<T> readDataFromDatabase(Connection connection) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public int size() throws SQLException, ClassNotFoundException, IOException {
        return 0;
    }

    @Override
    public Long searchCourse(String courseName) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Integer getTotalCreditsOfStudent(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Long searchPerson(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }
}
