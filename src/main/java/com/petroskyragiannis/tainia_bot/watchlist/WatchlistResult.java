package com.petroskyragiannis.tainia_bot.watchlist;

/**
 * Represents the result of a watchlist operation.
 *
 * <p>
 * This sealed interface models all possible outcomes of watchlist
 * commands in a type-safe and exhaustive manner.
 * </p>
 *
 * <p>
 * Each result variant may carry additional context relevant to
 * the specific outcome.
 * </p>
 */
public sealed interface WatchlistResult permits
        WatchlistResult.Added,
        WatchlistResult.Removed,
        WatchlistResult.AlreadyExists,
        WatchlistResult.NotFound {

    /**
     * Indicates that an item was successfully added to the watchlist.
     *
     * @param title title of the added item
     */
    record Added(String title) implements WatchlistResult {
    }

    /**
     * Indicates that an item was successfully removed from the watchlist.
     *
     * @param title title of the removed item
     */
    record Removed(String title) implements WatchlistResult {
    }

    /**
     * Indicates that the item already exists in the watchlist.
     */
    record AlreadyExists() implements WatchlistResult {
    }

    /**
     * Indicates that no matching item was found.
     */
    record NotFound() implements WatchlistResult {
    }

}
