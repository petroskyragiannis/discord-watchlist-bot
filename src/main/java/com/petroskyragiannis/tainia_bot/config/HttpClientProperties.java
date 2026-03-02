package com.petroskyragiannis.tainia_bot.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the HTTP client.
 */
@Validated
@ConfigurationProperties(prefix = "http.client")
public class HttpClientProperties {

    @Min(value = 100, message = "Connect timeout must be at least 100ms")
    private int connectTimeoutMs;

    @Min(value = 100, message = "Read timeout must be at least 100ms")
    private int readTimeoutMs;

    @Min(value = 100, message = "Connection request timeout must be at least 100ms")
    private int connectionRequestTimeoutMs;

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

    public int getConnectionRequestTimeoutMs() {
        return connectionRequestTimeoutMs;
    }

    public void setConnectionRequestTimeoutMs(int connectionRequestTimeoutMs) {
        this.connectionRequestTimeoutMs = connectionRequestTimeoutMs;
    }
}
