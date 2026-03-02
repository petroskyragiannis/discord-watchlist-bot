package com.petroskyragiannis.tainia_bot.discord;

import com.petroskyragiannis.tainia_bot.watchlist.WatchlistResult;
import com.petroskyragiannis.tainia_bot.watchlist.WatchlistService;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class DiscordInteractionHandler {

    private static final String REMOVE_SELECTION_ID = "watchlist:remove:menu";

    private final WatchlistService watchlistService;
    private final DiscordResponseRenderer responseRenderer;

    public DiscordInteractionHandler(WatchlistService watchlistService, DiscordResponseRenderer responseRenderer) {
        this.watchlistService = watchlistService;
        this.responseRenderer = responseRenderer;
    }

    public void handle(StringSelectInteractionEvent event) {

        if (!event.getComponentId().equals(REMOVE_SELECTION_ID)) return;

        Long guildId = event.getGuild().getIdLong();
        String title = event.getSelectedOptions().getFirst().getLabel();
        String imdbId = event.getSelectedOptions().getFirst().getValue();

        WatchlistResult result = watchlistService.removeFromWatchlistByImdbId(guildId, title, imdbId);

        responseRenderer.renderResult(event, result);
    }
}
