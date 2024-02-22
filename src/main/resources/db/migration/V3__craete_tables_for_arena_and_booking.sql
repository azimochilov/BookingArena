create table arena (
                       id serial primary key,
                       name varchar(255) not null unique,
                       description varchar(255),
                       status boolean default true,
                       user_id bigint references users(id) ON DELETE SET DEFAULT
);

create table reservation_arena (
                                   id serial primary key,
                                   created_at timestamp default current_timestamp,
                                   booking_from timestamp default current_timestamp,
                                   booking_to timestamp default current_timestamp,
                                   description varchar(500),
                                   total_price int,
                                   user_id bigint references users(id),
                                   costumer varchar(255)
);

create table booking (
                              id serial primary key,
                              reservation_arena_id bigint references reservation_arena(id),
                              user_id bigint references users(id)
);

create table arena_info (
                            id serial primary key,
                            phone varchar(255),
                            price int,
                            worked_from time,
                            worked_to time,
                            created_at timestamp default current_timestamp,
                            updated_at timestamp default current_timestamp,
                            address_id bigint references addresses(id),
                            arena_id bigint references arena(id)
);