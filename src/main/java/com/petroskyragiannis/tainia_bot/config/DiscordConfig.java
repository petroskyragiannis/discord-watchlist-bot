package com.petroskyragiannis.tainia_bot.config;

import com.petroskyragiannis.tainia_bot.discord.DiscordCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for initializing the Discord JDA client.
 *
 * <p>
 * This configuration:
 * <ul>
 *   <li>Builds the {@link JDA} instance using credentials and intents from {@link DiscordProperties}</li>
 *   <li>Registers the main Discord event listener</li>
 *   <li>Ensures a graceful shutdown of the Discord connection when the application stops</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableConfigurationProperties(DiscordProperties.class)
public class DiscordConfig {

    /**
     * Creates and configures the {@link JDA} client.
     *
     * @param properties       Discord configuration properties (token, gateway intents)
     * @param commandListener  primary listener for Discord interactions
     * @return initialized {@link JDA} instance
     */
    @Bean(destroyMethod = "shutdown")
    public JDA jda(DiscordProperties properties, DiscordCommandListener commandListener) {
        return JDABuilder
                .createDefault(properties.getToken(), properties.getGatewayIntents())   // Create a JDA client
                .addEventListeners(commandListener)   // Registers  event listener
                .build();
    }
}
