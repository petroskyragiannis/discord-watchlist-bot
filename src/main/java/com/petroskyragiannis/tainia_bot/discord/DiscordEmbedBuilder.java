package com.petroskyragiannis.tainia_bot.discord;

import com.petroskyragiannis.tainia_bot.watchlist.WatchlistItemDTO;
import com.petroskyragiannis.tainia_bot.watchlist.WatchlistItemType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;


public final class DiscordEmbedBuilder {

    public static MessageEmbed createEmbedForWatchlistItem(WatchlistItemDTO itemDTO) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setColor(colorForType(itemDTO.type()));
        embed.setTitle(itemDTO.title(), "https://www.imdb.com/title/" + itemDTO.imdbId());

        if (itemDTO.year() != null)
            embed.addField("Year", itemDTO.year(), true);

        if (itemDTO.runtime() != null)
            embed.addField("Runtime", itemDTO.runtime(), true);

        if (itemDTO.imdbRating() != null)
            embed.addField("Rating", itemDTO.imdbRating() + " ⭐", true);

        if (itemDTO.genres() != null)
            embed.addField("Genres", itemDTO.genres(), false);

        if (itemDTO.posterUrl() != null)
            embed.setImage(itemDTO.posterUrl());

        embed.setFooter(footerForType(itemDTO.type()));

        return embed.build();
    }

    private static Color colorForType(WatchlistItemType type) {
        return switch (type) {
            case MOVIES -> Color.RED;
            case SERIES -> Color.MAGENTA;
            case OTHER -> Color.GRAY;
        };
    }

    private static String footerForType(WatchlistItemType type) {
        return switch (type) {
            case MOVIES -> "🎥 Movie";
            case SERIES -> "🎞️ Series";
            case OTHER -> "🍿 Other";
        };
    }
}
