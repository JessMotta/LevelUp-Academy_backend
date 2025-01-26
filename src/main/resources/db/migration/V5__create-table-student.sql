CREATE TABLE IF NOT EXISTS t_lvup_student (
    id SERIAL PRIMARY KEY,
    experience_points INTEGER NOT NULL,
    current_patent VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    classroom_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES t_lvup_user(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES t_lvup_classroom(id) ON DELETE SET NULL
);
