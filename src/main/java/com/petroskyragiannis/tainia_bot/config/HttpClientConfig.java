package com.petroskyragiannis.tainia_bot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Spring configuration for HTTP client infrastructure.
 * <p>
 * This configuration creates a {@link RestTemplate} bean configured
 * with timeout values provided via {@link HttpClientProperties}.
 * </p>
 */
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientConfig {

    /**
     * Creates a {@link RestTemplate} configured with connection and read timeouts.
     *
     * <p>
     * Timeout values are externalized and validated via
     * {@link HttpClientProperties}.
     * </p>
     *
     * @param properties HTTP client timeout configuration
     * @return configured {@link RestTemplate} instance
     */
    @Bean
    public RestTemplate restTemplate(HttpClientProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(properties.getConnectTimeoutMs());
        factory.setReadTimeout(properties.getReadTimeoutMs());

        return new RestTemplate(factory);
    }

}
