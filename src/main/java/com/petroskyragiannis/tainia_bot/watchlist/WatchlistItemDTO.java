package com.petroskyragiannis.tainia_bot.watchlist;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a watchlist item for presentation purposes.
 */
public record WatchlistItemDTO(
        String imdbId,
        String title,
        String year,
        String runtime,
        String genres,
        String posterUrl,
        BigDecimal imdbRating,
        WatchlistItemType type
) {}
