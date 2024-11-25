package com.MTGCollectionTracker.MTGCollectionTrackerApplication.exceptions;

/**
 * created custom exception for Card not able to be found
 *
 * @author timmonsevan
 */
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String message) {
        super(message);
    }

    public CardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardNotFoundException(Throwable cause) {
        super(cause);
    }
}
