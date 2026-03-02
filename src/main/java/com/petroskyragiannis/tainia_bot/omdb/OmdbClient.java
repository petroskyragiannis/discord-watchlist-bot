package com.petroskyragiannis.tainia_bot.omdb;

import com.petroskyragiannis.tainia_bot.config.OmdbProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Client responsible for communicating with the OMDb API.
 *
 * <p>
 * This class handles HTTP request construction, execution, and
 * protocol-level error handling for OMDb interactions.
 * </p>
 *
 * <p>
 * It translates external API failures into OMDb-specific exceptions
 * that can be handled by higher layers.
 * </p>
 */
@Component
public class OmdbClient {

    private static final Logger log = LoggerFactory.getLogger(OmdbClient.class);

    private static final String QUERY_PARAM_API_KEY = "apikey";
    private static final String QUERY_PARAM_TITLE = "t";

    private final RestTemplate restTemplate;    // TODO: Migrate to WebClient
    private final OmdbProperties properties;

    public OmdbClient(RestTemplate restTemplate, OmdbProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * Fetches a movie or series from OMDb by title.
     *
     * @param title movie or series title
     * @return raw OMDb response DTO
     * @throws OmdbException if no matching title exists or a technical error occurs
     */
    public OmdbResponseDTO fetchByTitle(String title) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(properties.getBaseUrl())
                    .queryParam(QUERY_PARAM_API_KEY, properties.getKey())
                    .queryParam(QUERY_PARAM_TITLE, title)
                    .build()
                    .toUri();

            log.debug("OMDb request: title='{}'", title);

            ResponseEntity<OmdbResponseDTO> response = restTemplate.getForEntity(uri, OmdbResponseDTO.class);
            OmdbResponseDTO body = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful() || body == null) {
                log.error("OMDb invalid response [status={}]", response.getStatusCode());
                throw new OmdbException("OMDb invalid response");
            }

            // OMDb uses a logical "Response" field instead of HTTP status codes
            if (!"True".equalsIgnoreCase(body.getResponse())) {
                log.error("OMDb error response [error='{}']", body.getError());
                throw new OmdbException("OMDb error response");
            }

            log.debug("OMDb success [title='{}', imdbId={}]", body.getTitle(), body.getImdbId());

            return body;

        } catch (RestClientException ex) {
            log.error("OMDb communication failure [title='{}']", title, ex);
            throw new OmdbException("OMDb communication failure", ex);
        }
    }

}