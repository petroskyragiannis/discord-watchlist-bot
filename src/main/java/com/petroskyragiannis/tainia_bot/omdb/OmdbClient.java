package com.petroskyragiannis.tainia_bot.omdb;

import com.petroskyragiannis.tainia_bot.config.OmdbProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Reactive client for the OMDb API.
 */
@Component
public class OmdbClient {

    private static final Logger log = LoggerFactory.getLogger(OmdbClient.class);

    private static final String QUERY_PARAM_API_KEY = "apikey";
    private static final String QUERY_PARAM_TITLE = "t";

    private final WebClient webClient;
    private final OmdbProperties properties;

    public OmdbClient(WebClient webClient, OmdbProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    /**
     * Fetches a movie or series from OMDb by title.
     */
    public Mono<OmdbResponseDTO> fetchByTitle(String title) {
        URI uri = UriComponentsBuilder
                .fromUriString(properties.getBaseUrl())
                .queryParam(QUERY_PARAM_API_KEY, properties.getKey())
                .queryParam(QUERY_PARAM_TITLE, title)
                .build()
                .toUri();

        log.debug("OMDb request [title='{}']", title);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        status -> !status.is2xxSuccessful(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .then(Mono.defer(() -> {
                                    log.error("OMDb HTTP error [status={}]", clientResponse.statusCode());
                                    return Mono.error(new OmdbException("OMDb HTTP error"));
                                }))
                )
                .bodyToMono(OmdbResponseDTO.class)
                .switchIfEmpty(Mono.error(new OmdbException("OMDb empty response")))
                .flatMap(dto -> {
                    if (!"True".equalsIgnoreCase(dto.getResponse())) {
                        log.warn("OMDb error response [error='{}']", dto.getError());
                        return Mono.error(new OmdbException("OMDb error response"));
                    }
                    return Mono.just(dto);
                })
                .doOnSuccess(dto -> log.debug("OMDb success [title='{}', imdbId={}]", dto.getTitle(), dto.getImdbId()))
                .onErrorMap(WebClientException.class, ex -> {
                    log.error("OMDb communication failure [title='{}']", title, ex);
                    return new OmdbException("OMDb communication failure", ex);
                });
    }

}
