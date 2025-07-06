package exceptions;

/**
 * Exception thrown when an operation is attempted on an empty stack.
 *
 * This exception is thrown when trying to:
 * - pop() from an empty stack
 * - peek() at an empty stack
 *
 * @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

    /**
     * Serial version UID for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new EmptyStackException with no detail message.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructs a new EmptyStackException with the specified detail message.
     *
     * @param message the detail message explaining why the exception occurred
     */
    public EmptyStackException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmptyStackException with the specified detail message and cause.
     *
     * @param message the detail message explaining why the exception occurred
     * @param cause the cause of this exception (which is saved for later retrieval)
     */
    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EmptyStackException with the specified cause.
     *
     * @param cause the cause of this exception (which is saved for later retrieval)
     */
    public EmptyStackException(Throwable cause) {
        super(cause);
    }
}