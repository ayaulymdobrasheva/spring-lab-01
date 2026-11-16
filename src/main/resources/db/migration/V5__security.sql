CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(60) NOT NULL UNIQUE,
                          password_hash VARCHAR(100) NOT NULL,
                          enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE user_role (
                           user_id BIGINT NOT NULL
                               REFERENCES app_user(id),
                           role VARCHAR(20) NOT NULL,
                           PRIMARY KEY (user_id, role)
);
