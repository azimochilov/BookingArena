ALTER TABLE booking
DROP CONSTRAINT IF EXISTS booking_reservation_arena_id_fkey;

ALTER TABLE reservation_arena
DROP COLUMN IF EXISTS arena_id;
