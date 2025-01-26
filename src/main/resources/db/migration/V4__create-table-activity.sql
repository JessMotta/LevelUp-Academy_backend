CREATE TABLE IF NOT EXISTS t_lvup_activity (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    in_group BOOLEAN NOT NULL,
    students_per_group INTEGER,
    answer TEXT,
    execution VARCHAR(50) NOT NULL,
    prestige_value INTEGER NOT NULL,
    value_received INTEGER,
    classroom_id BIGINT,
    FOREIGN KEY (classroom_id) REFERENCES t_lvup_classroom(id) ON DELETE CASCADE
);
