CREATE TABLE IF NOT EXISTS t_lvup_student_classroom (
    student_id INT NOT NULL,
    classroom_id INT NOT NULL,
    PRIMARY KEY (student_id, classroom_id),
    FOREIGN KEY (student_id) REFERENCES t_lvup_student(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES t_lvup_classroom(id) ON DELETE CASCADE
);
