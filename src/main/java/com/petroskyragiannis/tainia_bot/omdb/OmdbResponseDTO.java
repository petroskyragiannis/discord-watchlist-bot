package com.petroskyragiannis.tainia_bot.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO mapping the OMDb API response payload.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbResponseDTO {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genres;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("imdbID")
    private String imdbId;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
