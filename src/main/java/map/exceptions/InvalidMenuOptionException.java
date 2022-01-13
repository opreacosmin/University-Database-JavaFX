package map.exceptions;


/**
 * class representing custom exception.
 * This exception will be thrown when the slected option doesn't exist in the menu options
 */
public class InvalidMenuOptionException extends Exception {

    public InvalidMenuOptionException(String str) { super(str); }
}