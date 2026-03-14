INSERT INTO exercises (name, muscle_group)
SELECT 'Bench Press', 'Chest' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Bench Press');

INSERT INTO exercises (name, muscle_group)
SELECT 'Squat', 'Legs' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Squat');

INSERT INTO exercises (name, muscle_group)
SELECT 'Deadlift', 'Back' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Deadlift');

INSERT INTO exercises (name, muscle_group)
SELECT 'Pull Up', 'Back' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Pull Up');

INSERT INTO exercises (name, muscle_group)
SELECT 'Overhead Press', 'Shoulders' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Overhead Press');

INSERT INTO exercises (name, muscle_group)
SELECT 'Bicep Curl', 'Arms' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Bicep Curl');

INSERT INTO exercises (name, muscle_group)
SELECT 'Tricep Dip', 'Arms' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Tricep Dip');

INSERT INTO exercises (name, muscle_group)
SELECT 'Leg Press', 'Legs' WHERE NOT EXISTS (SELECT 1 FROM exercises WHERE name = 'Leg Press');