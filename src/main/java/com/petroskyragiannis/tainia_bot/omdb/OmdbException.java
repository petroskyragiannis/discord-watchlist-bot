package com.petroskyragiannis.tainia_bot.omdb;

/**
 * Exception for OMDb integration failures.
 */
public class OmdbException extends RuntimeException {
    public OmdbException(String message) {
        super(message);
    }

    public OmdbException(String message, Throwable cause) {
        super(message, cause);
    }
}
