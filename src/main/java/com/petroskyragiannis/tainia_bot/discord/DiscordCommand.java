package com.petroskyragiannis.tainia_bot.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Arrays;


public enum DiscordCommand {

    WATCHLIST_ADD("watchlist", "Manage your Watchlist.",
            new SubcommandData("add", "Add a movie or series to your Watchlist.")
                    .addOption(OptionType.STRING, "title", "The title you want to add.", true)
    ),

    WATCHLIST_REMOVE("watchlist", "Manage your Watchlist.",
            new SubcommandData("remove", "Remove a movie or series from your Watchlist.")
                    .addOption(OptionType.STRING, "title", "The title you want to remove.", true)
    ),

    WATCHLIST_SHOW("watchlist", "Manage your Watchlist.",
            new SubcommandData("show", "Show your Watchlist.")
                    .addOptions(new OptionData(OptionType.STRING, "category", "Select a category", true)
                            .addChoice("all", "all")
                            .addChoice("movies", "movies")
                            .addChoice("series", "series")
                    )
    );

    private final String rootName;
    private final String rootDescription;
    private final SubcommandData subcommand;

    DiscordCommand(String rootName, String rootDescription, SubcommandData subcommand) {
        this.rootName = rootName;
        this.rootDescription = rootDescription;
        this.subcommand = subcommand;
    }

    public String getRootName() {
        return rootName;
    }

    public String getRootDescription() {
        return rootDescription;
    }

    public SubcommandData getSubcommand() {
        return subcommand;
    }

    public static DiscordCommand from(SlashCommandInteractionEvent event) {

        String rootName = event.getName();
        String subcommandName = event.getSubcommandName();

        return Arrays.stream(values())
                .filter(command ->
                        command.getRootName().equals(rootName) &&
                                command.getSubcommand().getName().equals(subcommandName)
                )
                .findFirst()
                .orElseThrow(() -> new DiscordUnknownCommandException("Unknown command: /" + rootName + " " + subcommandName));
    }

}
