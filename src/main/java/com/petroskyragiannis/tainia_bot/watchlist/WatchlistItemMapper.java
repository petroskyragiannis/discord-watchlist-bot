package com.petroskyragiannis.tainia_bot.watchlist;

import com.petroskyragiannis.tainia_bot.omdb.OmdbResponseDTO;

import java.math.BigDecimal;

/**
 * Utility class responsible for mapping watchlist-related objects.
 * All methods are static and side - effect free.
 *
 * <p>
 * This mapper converts data between:
 * <ul>
 *   <li>{@link OmdbResponseDTO} → {@link WatchlistItem} (persistence)</li>
 *   <li>{@link WatchlistItem} → {@link WatchlistItemDTO} (presentation)</li>
 * </ul>
 * </p>
 */
public final class WatchlistItemMapper {

    /**
     * Minimum number of IMDb votes required for a rating to be considered reliable.
     * Ratings with fewer votes are ignored to avoid misleading values.
     */
    private static final int MIN_IMDB_VOTES = 10_000;

    private WatchlistItemMapper() {
    }

    /**
     * Maps an {@link OmdbResponseDTO} to a {@link WatchlistItem} entity.
     *
     * <p>
     * Values returned by the OMDb API are normalized to account for
     * missing, empty, or {@code "N/A"} fields.
     * </p>
     *
     * @param dto     OMDb response DTO
     * @param guildId Discord guild ID
     * @return mapped {@link WatchlistItem}
     */
    public static WatchlistItem toEntity(OmdbResponseDTO dto, Long guildId) {
        return new WatchlistItem(
                guildId,
                normalize(dto.getImdbId()),
                normalize(dto.getTitle()),
                normalize(dto.getYear()),
                normalize(dto.getRuntime()),
                normalize(dto.getGenres()),
                normalize(dto.getPoster()),
                parseImdbRating(normalize(dto.getImdbRating()), normalize(dto.getImdbVotes())),
                parseType(dto.getType())
        );
    }

    /**
     * Maps a {@link WatchlistItem} entity to a {@link WatchlistItemDTO}.
     *
     * @param item persisted watchlist item
     * @return DTO suitable for presentation
     */
    public static WatchlistItemDTO toDto(WatchlistItem item) {
        return new WatchlistItemDTO(
                item.getImdbId(),
                item.getTitle(),
                item.getYear(),
                item.getRuntime(),
                item.getGenres(),
                item.getPosterUrl(),
                item.getImdbRating(),
                item.getType()
        );
    }

    /**
     * Parses the IMDb rating if sufficient vote data is available.
     *
     * <p>
     * Returns {@code null} when:
     * <ul>
     *   <li>Rating or vote count is missing</li>
     *   <li>Vote count is below {@link #MIN_IMDB_VOTES}</li>
     *   <li>Values cannot be parsed</li>
     * </ul>
     * </p>
     */
    private static BigDecimal parseImdbRating(String ratingStr, String votesStr) {
        if (ratingStr == null || votesStr == null) return null;
        try {
            int votes = Integer.parseInt(votesStr.replace(",", ""));
            if (votes < MIN_IMDB_VOTES) return null;
            return new BigDecimal(ratingStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    /**
     * Maps the OMDb {@code type} field to a {@link WatchlistItemType}.
     *
     * @param type OMDb media type
     * @return mapped watchlist item type
     */
    private static WatchlistItemType parseType(String type) {
        return switch (type.toLowerCase()) {
            case "movie" -> WatchlistItemType.MOVIES;
            case "series" -> WatchlistItemType.SERIES;
            default -> WatchlistItemType.OTHER;
        };
    }

    /**
     * Normalizes external string values.
     *
     * <p>
     * Converts {@code null}, empty strings, and {@code "N/A"} values into {@code null}.
     * Otherwise, trims the value.
     * </p>
     */
    private static String normalize(String value) {
        return value == null || value.isEmpty() || "N/A".equalsIgnoreCase(value)
                ? null : value.trim();
    }
}
