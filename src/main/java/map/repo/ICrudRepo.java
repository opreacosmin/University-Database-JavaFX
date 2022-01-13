package map.repo;

import map.exceptions.NullValueException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

/**
 * CRUD operations repository interface
 *
 * @param <T> generic class
 */
public interface ICrudRepo<T> {

    /**
     * Searches for the object with the specified id.
     * Returns the object with the specified id or null - if there is no object with the given id. id must not be null
     *
     * @param id -the id of the object to be searched for
     * @return the object with the specified id or null
     */
    T findOne(Long id) throws NullValueException, SQLException, IOException, ClassNotFoundException;

    /**
     * Adds an object to the repository.
     * Returns null- if the given object is saved otherwise returns the object (id already exists). object must be not null
     *
     * @param obj to be saved
     * @return null or the already existing object
     */
    T create(T obj)throws NullValueException,IOException, SQLException, ClassNotFoundException;

    /**
     * @return all entities
     */
    List<T> getAll() throws SQLException, IOException, ClassNotFoundException;

    /**
     * Updates the object in the repository with the object got as parameter.
     * Returns null - if the object is updated, otherwise returns the object - (e.g id does not exist). object must not be null
     *
     * @param obj- to be updated with
     * @return null or the not existing object
     */
    T update(T obj) throws  NullValueException,  SQLException, IOException, ClassNotFoundException;

    /**
     * removes the object with the specified id.
     * Returns the removed object or null if there is no object with the given id. id must be not null
     *
     * @param id -the id of the object to be removed
     * @return the removed object or null
     */
    T delete(Long id) throws NullValueException, SQLException, IOException, ClassNotFoundException;
    Connection openConnection() throws ClassNotFoundException, SQLException, IOException;

    void closeConnection(Connection connection) throws SQLException;

    abstract List<T> readDataFromDatabase(Connection connection) throws SQLException, IOException, ClassNotFoundException;

    int size() throws SQLException, ClassNotFoundException, IOException;

    Long searchCourse(String courseName) throws SQLException, IOException, ClassNotFoundException;
    Integer getTotalCreditsOfStudent(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException;

    Long searchPerson(String firstName, String lastName) throws SQLException, IOException, ClassNotFoundException;
}
