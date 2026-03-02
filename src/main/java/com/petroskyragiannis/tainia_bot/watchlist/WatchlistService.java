package com.petroskyragiannis.tainia_bot.watchlist;

import com.petroskyragiannis.tainia_bot.omdb.OmdbException;
import com.petroskyragiannis.tainia_bot.omdb.OmdbResponseDTO;
import com.petroskyragiannis.tainia_bot.omdb.OmdbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Application service responsible for managing watchlist operations.
 *
 * <p>
 * This service coordinates persistence, external API calls, and domain
 * transformations, and returns structured {@link WatchlistResult}
 * outcomes to the calling layer.
 * </p>
 */
@Service
public class WatchlistService {

    private static final Logger log = LoggerFactory.getLogger(WatchlistService.class);

    private final WatchlistRepository watchlistRepository;
    private final OmdbService omdbService;

    public WatchlistService(WatchlistRepository watchlistRepository, OmdbService omdbService) {
        this.watchlistRepository = watchlistRepository;
        this.omdbService = omdbService;
    }

    /**
     * Adds a movie or series to a guild's watchlist using its title.
     *
     * @param guildId Discord guild ID
     * @param title   movie or series title
     * @return result of the add operation
     */
    @Transactional
    public WatchlistResult addToWatchlistByTitle(Long guildId, String title) {
        try {
            OmdbResponseDTO omdbResponseDTO = omdbService.getByTitle(title);

            if (omdbResponseDTO == null) {
                return new WatchlistResult.NotFound();
            }

            if (watchlistRepository.existsByGuildIdAndImdbId(guildId, omdbResponseDTO.getImdbId())) {
                log.info("Watchlist item already exists [guildId={}, imdbId={}]", guildId, omdbResponseDTO.getImdbId());
                return new WatchlistResult.AlreadyExists();
            }

            WatchlistItem item = WatchlistItemMapper.toEntity(omdbResponseDTO, guildId);
            watchlistRepository.save(item);
            log.info("Added watchlist item [guildId={}, title='{}', imdbId={}]", guildId, item.getTitle(), item.getImdbId());

            WatchlistItemDTO itemDTO = WatchlistItemMapper.toDto(item);
            return new WatchlistResult.Added(formatTitle(itemDTO));

        } catch (OmdbException ex) {
            return new WatchlistResult.NotFound();
        }
    }

    /**
     * Finds watchlist items by partial title match.
     */
    @Transactional(readOnly = true)
    public List<WatchlistItemDTO> findByTitle(Long guildId, String title) {
        if (title == null || title.isBlank()) return Collections.emptyList();
        return watchlistRepository
                .findAllByGuildIdAndTitleIgnoreCaseContaining(guildId, title)
                .stream()
                .map(WatchlistItemMapper::toDto)
                .toList();
    }

    /**
     * Removes a watchlist item by IMDb ID.
     */
    @Transactional
    public WatchlistResult removeFromWatchlistByImdbId(Long guildId, String title, String imdbId) {
        long deleted = watchlistRepository.deleteByGuildIdAndImdbId(guildId, imdbId);
        if (deleted == 0) return new WatchlistResult.NotFound();
        log.info("Removed watchlist item [guildId={}, imdbId={}]", guildId, imdbId);
        return new WatchlistResult.Removed(title);
    }

    /**
     * Retrieves all watchlist items for a guild.
     */
    @Transactional(readOnly = true)
    public List<WatchlistItemDTO> getWatchlist(Long guildId) {
        return watchlistRepository
                .findAllByGuildId(guildId)
                .stream()
                .map(WatchlistItemMapper::toDto)
                .toList();
    }

    /**
     * Retrieves watchlist items filtered by type.
     */
    @Transactional(readOnly = true)
    public List<WatchlistItemDTO> getWatchlistByType(Long guildId, WatchlistItemType type) {
        return watchlistRepository
                .findAllByGuildIdAndType(guildId, type)
                .stream()
                .map(WatchlistItemMapper::toDto)
                .toList();
    }

    private String formatTitle(WatchlistItemDTO dto) {
        return dto.year() != null
                ? dto.title() + " (" + dto.year() + ")"
                : dto.title();
    }
}
