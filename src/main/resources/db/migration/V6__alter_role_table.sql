ALTER TABLE roles
    ADD COLUMN IF NOT EXISTS role_privileges_id BIGINT;

ALTER TABLE roles
    ADD CONSTRAINT FK_role_role_privileges
        FOREIGN KEY (role_privileges_id) REFERENCES role_privileges (id);