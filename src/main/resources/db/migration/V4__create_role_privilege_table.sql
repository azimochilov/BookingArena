CREATE TABLE IF NOT EXISTS role_privileges (
    id SERIAL PRIMARY KEY,
    privilege_id BIGINT,
    role_id BIGINT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(privilege_id, role_id),
    FOREIGN KEY (privilege_id) REFERENCES privileges (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
    );