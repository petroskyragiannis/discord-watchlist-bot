package com.petroskyragiannis.tainia_bot.watchlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link WatchlistItem} persistence operations.
 */
@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistItem, UUID> {

    boolean existsByGuildIdAndImdbId(Long guildId, String imdbId);

    long deleteByGuildIdAndImdbId(Long guildId, String imdbId);

    List<WatchlistItem> findAllByGuildId(Long guildId);

    List<WatchlistItem> findAllByGuildIdAndType(Long guildId, WatchlistItemType type);

    List<WatchlistItem> findAllByGuildIdAndTitleIgnoreCaseContaining(Long guildId, String title);
}
