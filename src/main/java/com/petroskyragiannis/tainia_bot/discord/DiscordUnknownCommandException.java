package com.petroskyragiannis.tainia_bot.discord;

public class DiscordUnknownCommandException extends RuntimeException {

    public DiscordUnknownCommandException(String message) {
        super(message);
    }

    public DiscordUnknownCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}