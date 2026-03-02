package com.petroskyragiannis.tainia_bot.omdb;

import org.springframework.stereotype.Service;

/**
 * Application service providing access to OMDb data.
 *
 * <p>
 * This service acts as a thin façade over {@link OmdbClient},
 * performing basic input validation and delegating external
 * API calls to the client layer.
 * </p>
 */
@Service
public class OmdbService {

    private final OmdbClient omdbClient;

    public OmdbService(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    /**
     * Retrieves movie or series details by title.
     *
     * @param title movie or series title
     * @return raw OMDb response DTO
     * @throws IllegalArgumentException if the title is {@code null} or blank
     * @throws OmdbException            if a technical OMDb error occurs
     */
    public OmdbResponseDTO getByTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title must not be null or blank");
        return omdbClient.fetchByTitle(title.trim());
    }
}
