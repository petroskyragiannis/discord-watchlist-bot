package com.petroskyragiannis.tainia_bot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * HTTP client configuration.
 */
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientConfig {

    /**
     * Creates a {@link WebClient} configured with timeouts.
     */
    @Bean
    public WebClient webClient(HttpClientProperties properties) {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("default")
                .pendingAcquireTimeout(Duration.ofMillis(properties.getConnectionRequestTimeoutMs()))
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectTimeoutMs())
                .responseTimeout(Duration.ofMillis(properties.getReadTimeoutMs()))
                .doOnConnected(connection -> connection.addHandlerLast(
                        new ReadTimeoutHandler(properties.getReadTimeoutMs(), TimeUnit.MILLISECONDS)
                ));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
