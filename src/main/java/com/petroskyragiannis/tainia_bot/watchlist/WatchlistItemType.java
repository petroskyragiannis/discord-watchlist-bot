package com.petroskyragiannis.tainia_bot.watchlist;

/**
 * Enumeration of supported watchlist item types.
 *
 * <p>
 * This enum represents the normalized media categories stored in the
 * application's domain and database.
 * </p>
 *
 * <p>
 * Values are derived from external sources (e.g. OMDb API) but mapped
 * explicitly to avoid leaking external representations into the domain.
 * </p>
 */
public enum WatchlistItemType {
    MOVIES,
    SERIES,
    OTHER
}
