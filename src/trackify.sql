USE trackify;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE habits (
    habit_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE habit_completions (
    completion_id INT PRIMARY KEY AUTO_INCREMENT,
    habit_id INT NOT NULL,
    user_id INT NOT NULL,
    date_completed DATE NOT NULL,
    FOREIGN KEY (habit_id) REFERENCES habits(habit_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE habit_schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    habit_id INT,
    day_of_week ENUM('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'),
    FOREIGN KEY (habit_id) REFERENCES habits(habit_id)
);
CREATE TABLE habit_progress (
    progress_id INT AUTO_INCREMENT PRIMARY KEY,
    habit_id INT,
    date DATE,
    completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (habit_id) REFERENCES habits(habit_id),
    UNIQUE (habit_id, date) -- To avoid duplicate entries
);
CREATE TABLE streaks (
    user_id INT PRIMARY KEY,
    current_streak INT DEFAULT 0,
    longest_streak INT DEFAULT 0,
    last_updated DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
