package com.petroskyragiannis.tainia_bot.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.petroskyragiannis.tainia_bot.discord.DiscordResponseMessage.*;

@Component
public class DiscordCommandListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(DiscordCommandListener.class);

    private final DiscordCommandRegistrar commandRegistrar;
    private final DiscordCommandHandler commandHandler;
    private final DiscordInteractionHandler interactionHandler;

    public DiscordCommandListener(DiscordCommandRegistrar commandRegistrar, DiscordCommandHandler commandHandler, DiscordInteractionHandler interactionHandler) {
        this.commandRegistrar = commandRegistrar;
        this.commandHandler = commandHandler;
        this.interactionHandler = interactionHandler;
    }

    @Override
    public void onReady(ReadyEvent event) {
        log.info("Bot ready as {} | connected to {} guild(s)", event.getJDA().getSelfUser().getAsTag(), event.getJDA().getGuilds().size());
        commandRegistrar.registerCommands(event.getJDA());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getGuild() == null) {
            event.reply(NOT_IN_SERVER_MESSAGE.raw())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        try {

            commandHandler.handle(event);

        } catch (DiscordUnknownCommandException ex) {
            event.getHook()
                    .sendMessage(UNKNOWN_COMMAND_MESSAGE.raw())
                    .setEphemeral(true)
                    .queue();
        } catch (Exception ex) {
            event.getHook()
                    .sendMessage(ERROR_MESSAGE.raw())
                    .setEphemeral(true)
                    .queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {

        if (event.getGuild() == null) {
            event.reply(NOT_IN_SERVER_MESSAGE.raw())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        try {

            interactionHandler.handle(event);

        } catch (Exception ex) {
            event.getHook()
                    .sendMessage(ERROR_MESSAGE.raw())
                    .setEphemeral(true)
                    .queue();
        }
    }
}
