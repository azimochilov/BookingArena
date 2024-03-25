CREATE TABLE booking (
                         id SERIAL PRIMARY KEY,
                         consumer VARCHAR(255),
                         created_at TIMESTAMP WITH TIME ZONE,
                         booking_from TIMESTAMP WITH TIME ZONE,
                         booking_to TIMESTAMP WITH TIME ZONE,
                         description VARCHAR(255),
                         total_price INT,
                         costumer VARCHAR(255),
                         arena_id BIGINT,
                         user_id BIGINT,
                         CONSTRAINT fk_booking_arena FOREIGN KEY (arena_id) REFERENCES arena(id),
                         CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(id)
);
