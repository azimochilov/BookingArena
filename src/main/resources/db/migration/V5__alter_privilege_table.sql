ALTER TABLE privileges
    ADD COLUMN IF NOT EXISTS role_privileges_id BIGINT;

ALTER TABLE privileges
    ADD CONSTRAINT FK_privilege_role_privileges
        FOREIGN KEY (role_privileges_id) REFERENCES role_privileges (id);