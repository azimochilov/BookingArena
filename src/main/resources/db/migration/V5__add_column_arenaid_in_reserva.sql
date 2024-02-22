ALTER TABLE reservation_arena
    ADD COLUMN arena_id BIGINT REFERENCES arena(id);