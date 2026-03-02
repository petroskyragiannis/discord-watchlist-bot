CREATE TABLE watchlist_item (
    id UUID PRIMARY KEY,

    guild_id BIGINT NOT NULL,
    imdb_id VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,

    year VARCHAR(20),
    runtime VARCHAR(20),
    genres TEXT,
    poster_url TEXT,

    imdb_rating NUMERIC(3,1),

    CONSTRAINT uk_watchlist_item_guild_imdb
        UNIQUE (guild_id, imdb_id)
);
