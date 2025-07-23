package exceptions;

/**
 * Exception thrown when attempting to perform operations on an empty queue
 * that require the queue to contain at least one element.
 *
 * @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu Samuel Braun
 * @version 1.0
 */
public class EmptyQueueException extends RuntimeException {

    /**
     * Serial version UID for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new EmptyQueueException with no detail message.
     */
    public EmptyQueueException() {
        super();
    }

    /**
     * Constructs a new EmptyQueueException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public EmptyQueueException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmptyQueueException with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the cause of the exception
     * @param cause the cause of the exception
     */
    public EmptyQueueException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EmptyQueueException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public EmptyQueueException(Throwable cause) {
        super(cause);
    }
}