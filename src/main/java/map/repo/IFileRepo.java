package map.repo;

import java.io.IOException;
/**
 * Interface for file operations
 *
 * @param <T> generic class, represents the class of objects that will be stored in the repository
 */
public interface IFileRepo <T> extends ICrudRepo<T>{
    /**
     * reads data from Json file and saves it in repoList
     *
     * @throws IOException if the file is invalid
     */
    void readFromFile() throws IOException;

    /**
     * writes repoList in the file
     *
     * @throws IOException if the file is invalid
     */
    void writeToFile() throws IOException;

}
