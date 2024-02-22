CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       email VARCHAR(255),
                       address_id BIGINT,
                       is_active BOOLEAN DEFAULT false,
                       FOREIGN KEY (address_id) REFERENCES addresses(id)
);

create table role (


                      id serial primary key,
                      name varchar(80) not null unique,
                      created_at timestamp default current_timestamp,
                      updated_at timestamp default current_timestamp
);

create table privilege (
                           id serial primary key,
                           name varchar(80) not null unique,
                           created_at timestamp default current_timestamp,
                           updated_at timestamp default current_timestamp
);

create table role_privilege (
                                id serial primary key,
                                role_id bigint REFERENCES role(id),
                                privilege_id bigint REFERENCES privilege(id)
);

alter table users
    add column role_id bigint REFERENCES role(id) ON DELETE SET DEFAULT,
add constraint u_name_unique unique(username, email);