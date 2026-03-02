package com.petroskyragiannis.tainia_bot.omdb;

import org.springframework.stereotype.Service;

/**
 * Application service providing access to OMDb data.
 */
@Service
public class OmdbService {

    private final OmdbClient omdbClient;

    public OmdbService(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    /**
     * Retrieves movie or series details by title.
     */
    public OmdbResponseDTO getByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be null or blank");
        }
        return omdbClient.fetchByTitle(title.trim()).block();
    }
}
