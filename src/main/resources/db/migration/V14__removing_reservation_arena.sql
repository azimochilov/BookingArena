
ALTER TABLE reservation_arena
DROP COLUMN IF EXISTS arena_id;

ALTER TABLE booking
DROP CONSTRAINT IF EXISTS FK_reservation_arena;


