package org.entitypedia.games.common.exceptions;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class GamesCommonException extends RuntimeException {

    /**
     * Constructor.
     * Creates a new Exception by using super(msg) method.
     *
     * @param errorDescription the description of the error
     */
    public GamesCommonException(String errorDescription) {
        super(errorDescription);
    }

    /**
     * Constructor.
     * Creates a new Exception by using super(msg, cause) method.
     *
     * @param errorDescription the description of the error
     * @param cause            the cause
     */
    public GamesCommonException(String errorDescription, Throwable cause) {
        super(errorDescription, cause);
    }
}