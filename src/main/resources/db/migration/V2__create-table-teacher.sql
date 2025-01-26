CREATE TABLE IF NOT EXISTS t_lvup_teacher (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_lvup_user(id) ON DELETE CASCADE
);
