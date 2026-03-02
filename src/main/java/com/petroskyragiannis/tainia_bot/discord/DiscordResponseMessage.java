package com.petroskyragiannis.tainia_bot.discord;
/**
 * Centralized Discord user-facing messages.
 *
 * <p>
 * This enum acts as a message catalog of the Discord layer.
 * </p>
 */
public enum DiscordResponseMessage {
    ADDED_MESSAGE("✅ Added %s to your Watchlist."),
    REMOVED_MESSAGE("✅ Removed %s from your Watchlist."),
    NOT_FOUND_MESSAGE("❌ No movies or series found."),
    ALREADY_EXISTS_MESSAGE("⚠️ Already exists in your Watchlist."),
    REMOVE_MENU_MESSAGE("🔍 Select one to remove from your Watchlist"),
    EMPTY_MESSAGE("🗑️ Your Watchlist is empty."),
    UNKNOWN_COMMAND_MESSAGE("⚠️ Unknown command."),
    ERROR_MESSAGE("⚠️ An error occurred. Please try again later."),
    NOT_IN_SERVER_MESSAGE("⚠️ This command can only be used in a server.");

    private final String template;

    DiscordResponseMessage(String template) {
        this.template = template;
    }

    /**
     * Formats the message with optional parameters.
     */
    public String format(Object... args) {
        return String.format(template, args);
    }

    /**
     * Returns the raw message without formatting.
     */
    public String raw() {
        return template;
    }
}
