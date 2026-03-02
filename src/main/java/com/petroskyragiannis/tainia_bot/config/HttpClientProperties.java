package com.petroskyragiannis.tainia_bot.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for HTTP client behavior.
 */
@Validated
@ConfigurationProperties(prefix = "http.client")
public class HttpClientProperties {

    @Min(value = 100, message = "Connect timeout must be at least 100ms")
    private int connectTimeoutMs;

    @Min(value = 100, message = "Read timeout must be at least 100ms")
    private int readTimeoutMs;

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getReadTimeoutMs() {
        return readTimeoutMs;
    }

    public void setReadTimeoutMs(int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }
}
