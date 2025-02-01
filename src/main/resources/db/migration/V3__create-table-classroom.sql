CREATE TABLE IF NOT EXISTS t_lvup_classroom (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    time_spot INTEGER NOT NULL,
    teacher_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES t_lvup_teacher(id) ON DELETE SET NULL
);
