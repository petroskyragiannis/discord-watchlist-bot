package com.petroskyragiannis.tainia_bot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DiscordCommandRegistrar {

    private static final Logger log = LoggerFactory.getLogger(DiscordCommandRegistrar.class);


    public void registerCommands(JDA jda) {

        // Map storing (Root Name, Slash Command) Pairs
        Map<String, SlashCommandData> slashCommandsMap = new LinkedHashMap<>();

        for (DiscordCommand command : DiscordCommand.values()) {

            // Create a Slash Command per Root Name
            slashCommandsMap.computeIfAbsent(
                    command.getRootName(),
                    rootName -> Commands.slash(rootName, command.getRootDescription())
            );

            // Attach Subcommands to Slash Command
            if (command.getSubcommand() != null) {
                slashCommandsMap.get(command.getRootName()).addSubcommands(command.getSubcommand());
            }
        }

        // Register Commands
        jda.getGuilds().forEach(guild ->
                guild.updateCommands()
                        .addCommands(slashCommandsMap.values())
                        .queue(
                                success -> log.info("Registered {} command(s) for guild '{}'", slashCommandsMap.size(), guild.getName()),
                                error -> log.error("Failed to register commands for guild '{}'", guild.getName(), error)
                        )
        );
    }

}
