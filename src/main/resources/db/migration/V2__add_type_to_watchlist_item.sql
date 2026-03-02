ALTER TABLE watchlist_item
    ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'OTHER';

ALTER TABLE watchlist_item
    ADD CONSTRAINT chk_watchlist_item_type
        CHECK (type IN ('MOVIES', 'SERIES', 'OTHER'));

CREATE INDEX idx_watchlist_item_guild_type_title
    ON watchlist_item (guild_id, type, title);