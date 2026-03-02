package com.petroskyragiannis.tainia_bot.watchlist;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * JPA entity representing a single item in a guild's watchlist.
 *
 * <p>
 * A watchlist item corresponds to a movie or series retrieved from the OMDb API
 * and stored for a specific Discord guild.
 * </p>
 *
 * <p>
 * Uniqueness is enforced at the database level using the combination
 * of {@code guild_id} and {@code imdb_id}.
 * </p>
 */
@Entity
@Table(
        name = "watchlist_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"guild_id", "imdb_id"})
)
public class WatchlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "guild_id", nullable = false)
    private Long guildId;

    @Column(name = "imdb_id", nullable = false, length = 20)
    private String imdbId;

    @Column(nullable = false)
    private String title;

    @Column(length = 20)
    private String year;

    @Column(length = 20)
    private String runtime;

    @Column
    private String genres;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "imdb_rating", precision = 3, scale = 1)
    private BigDecimal imdbRating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WatchlistItemType type;

    protected WatchlistItem() {
    }

    public WatchlistItem(Long guildId, String imdbId, String title, String year, String runtime, String genres, String posterUrl, BigDecimal imdbRating, WatchlistItemType watchlistItemType) {
        this.guildId = Objects.requireNonNull(guildId, "guildId is required");
        this.imdbId = Objects.requireNonNull(imdbId, "imdbId is required");
        this.title = Objects.requireNonNull(title, "title is required");
        this.year = year;
        this.runtime = runtime;
        this.genres = genres;
        this.posterUrl = posterUrl;
        this.imdbRating = imdbRating;
        this.type = Objects.requireNonNull(watchlistItemType, "type  is required");
    }

    public UUID getId() {
        return id;
    }

    public Long getGuildId() {
        return guildId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public BigDecimal getImdbRating() {
        return imdbRating;
    }

    public WatchlistItemType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WatchlistItem other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
