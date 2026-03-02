package com.petroskyragiannis.tainia_bot.discord;

import com.petroskyragiannis.tainia_bot.watchlist.WatchlistItemDTO;
import com.petroskyragiannis.tainia_bot.watchlist.WatchlistResult;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.petroskyragiannis.tainia_bot.discord.DiscordResponseMessage.*;

@Component
public class DiscordResponseRenderer {

    public void renderResult(SlashCommandInteractionEvent event, WatchlistResult result) {
        // Create Response
        String response = createResponseFromResult(result);

        // Send Response
        event.getHook()
                .sendMessage(response)
                .queue();
    }

    public void renderResult(StringSelectInteractionEvent event, WatchlistResult result) {
        // Create Response
        String response = createResponseFromResult(result);

        // Send Response
        event.reply(response).queue();

        // Clear Selection Menu
        event.getMessage().editMessageComponents().queue();

    }

    public void renderRemoveSelectionMenu(SlashCommandInteractionEvent event, List<WatchlistItemDTO> items) {
        // Create Menu
        StringSelectMenu.Builder menu = StringSelectMenu.create("watchlist:remove:menu");

        // Add Options to Menu
        items.forEach(item ->
                menu.addOption(
                        item.title() + " (" + item.year() + ")",
                        item.imdbId()
                )
        );

        // Send Response (Menu)
        event.getHook()
                .sendMessage(REMOVE_MENU_MESSAGE.raw())
                .setComponents(ActionRow.of(menu.build()))
                .queue();
    }

    public void renderWatchlist(SlashCommandInteractionEvent event, List<WatchlistItemDTO> items) {

        if (items.isEmpty()) {
            event.getHook()
                    .sendMessage(EMPTY_MESSAGE.raw()
                    )
                    .queue();
            return;
        }

        event.getHook()
                .sendMessageEmbeds(
                        items.stream()
                                .map(DiscordEmbedBuilder::createEmbedForWatchlistItem)
                                .toList()
                )
                .queue();
    }

    private String createResponseFromResult(WatchlistResult result) {
        return switch (result) {
            case WatchlistResult.Added(String title) -> ADDED_MESSAGE.format(title);
            case WatchlistResult.Removed(String title) -> REMOVED_MESSAGE.format(title);
            case WatchlistResult.NotFound() -> NOT_FOUND_MESSAGE.raw();
            case WatchlistResult.AlreadyExists() -> ALREADY_EXISTS_MESSAGE.raw();
        };
    }

}
