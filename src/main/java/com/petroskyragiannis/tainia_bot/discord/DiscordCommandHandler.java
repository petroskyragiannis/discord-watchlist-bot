package com.petroskyragiannis.tainia_bot.discord;

import com.petroskyragiannis.tainia_bot.watchlist.WatchlistItemDTO;
import com.petroskyragiannis.tainia_bot.watchlist.WatchlistItemType;
import com.petroskyragiannis.tainia_bot.watchlist.WatchlistResult;
import com.petroskyragiannis.tainia_bot.watchlist.WatchlistService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DiscordCommandHandler {

    private final WatchlistService watchlistService;
    private final DiscordResponseRenderer responseRenderer;

    public DiscordCommandHandler(DiscordResponseRenderer responseRenderer, WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
        this.responseRenderer = responseRenderer;
    }

    public void handle(SlashCommandInteractionEvent event) {
        DiscordCommand command = DiscordCommand.from(event);
        switch (command) {
            case WATCHLIST_ADD -> handleAdd(event);
            case WATCHLIST_REMOVE -> handleRemove(event);
            case WATCHLIST_SHOW -> handleShow(event);
            default -> throw new IllegalStateException("No handler implemented for command: " + command);
        }
    }

    private void handleAdd(SlashCommandInteractionEvent event) {
        // Ephemeral: User-Only Response
        event.deferReply(true).queue();

        Long guildId = Objects.requireNonNull(event.getGuild()).getIdLong();
        String title = Objects.requireNonNull(event.getOption("title")).getAsString();

        WatchlistResult result = watchlistService.addToWatchlistByTitle(guildId, title);

        responseRenderer.renderResult(event, result);
    }

    private void handleRemove(SlashCommandInteractionEvent event) {
        // Ephemeral: User-Only Response
        event.deferReply(true).queue();

        Long guildId = Objects.requireNonNull(event.getGuild()).getIdLong();
        String title = Objects.requireNonNull(event.getOption("title")).getAsString();

        List<WatchlistItemDTO> matches = watchlistService.findByTitle(guildId, title);

        if (matches.isEmpty()) {
            responseRenderer.renderResult(event, new WatchlistResult.NotFound());
            return;
        }

        if (matches.size() == 1) {
            //TODO Change Title
            WatchlistResult result = watchlistService.removeFromWatchlistByImdbId(guildId, matches.getFirst().title(), matches.getFirst().imdbId());
            responseRenderer.renderResult(event, result);
            return;
        }

        responseRenderer.renderRemoveSelectionMenu(event, matches);
    }

    private void handleShow(SlashCommandInteractionEvent event) {
        // Not Ephemeral: Public Response
        event.deferReply(false).queue(); // Public response

        Long guildId = Objects.requireNonNull(event.getGuild()).getIdLong();
        String category = Objects.requireNonNull(event.getOption("category")).getAsString();

        switch (category) {
            case "all" -> responseRenderer.renderWatchlist(event, watchlistService.getWatchlist(guildId));
            case "movies" -> responseRenderer.renderWatchlist(event, watchlistService.getWatchlistByType(guildId, WatchlistItemType.MOVIES));
            case "series" -> responseRenderer.renderWatchlist(event, watchlistService.getWatchlistByType(guildId, WatchlistItemType.SERIES));
            default -> throw new IllegalStateException("Unsupported watchlist type: " + category);
        }
    }

}
