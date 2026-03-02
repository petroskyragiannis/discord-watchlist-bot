package com.petroskyragiannis.tainia_bot.omdb;

public class OmdbException extends RuntimeException {

    /**
     * Base exception for unexpected failures when communicating with the OMDb API.
     *
     * <p>
     * This exception represents technical or protocol-level errors such as:
     * <ul>
     *   <li>Network communication failures</li>
     *   <li>Invalid or malformed responses</li>
     *   <li>Unexpected API behavior</li>
     * </ul>
     * </p>
     */
    public OmdbException(String message) {
        super(message);
    }

    public OmdbException(String message, Throwable cause) {
        super(message, cause);
    }
}