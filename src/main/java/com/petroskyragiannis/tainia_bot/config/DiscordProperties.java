package com.petroskyragiannis.tainia_bot.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * Configuration properties for the Discord bot integration.
 */
@Validated
@ConfigurationProperties(prefix = "discord.bot")
public class DiscordProperties {

    @NotBlank(message = "Discord token must not be blank")
    private String token;

    @NotEmpty(message = "At least one Discord gateway intent must be configured")
    private Set<GatewayIntent> gatewayIntents;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<GatewayIntent> getGatewayIntents() {
        return gatewayIntents;
    }

    public void setGatewayIntents(Set<GatewayIntent> gatewayIntents) {
        this.gatewayIntents = gatewayIntents;
    }
}
