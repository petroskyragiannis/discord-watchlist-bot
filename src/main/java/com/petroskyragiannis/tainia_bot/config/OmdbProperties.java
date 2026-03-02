package com.petroskyragiannis.tainia_bot.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the OMDb API integration.
 */
@Validated
@ConfigurationProperties(prefix = "omdb.api")
public class OmdbProperties {

    @NotBlank(message = "OMDb base URL must not be blank")
    @Pattern(
            regexp = "^https?://.*",
            message = "OMDb base URL must be a valid HTTP or HTTPS URL"
    )
    private String baseUrl;

    @NotBlank(message = "OMDb API key must not be blank")
    private String key;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
