package com.petroskyragiannis.tainia_bot.config;

import com.petroskyragiannis.tainia_bot.discord.DiscordCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for the Discord JDA client.
 */
@Configuration
@EnableConfigurationProperties(DiscordProperties.class)
public class DiscordConfig {

    /**
     * Creates the {@link JDA} client with the configured intents and listener.
     */
    @Bean(destroyMethod = "shutdown")
    public JDA jda(DiscordProperties properties, DiscordCommandListener commandListener) {
        return JDABuilder
                .createDefault(properties.getToken(), properties.getGatewayIntents())
                .addEventListeners(commandListener)
                .build();
    }
}
