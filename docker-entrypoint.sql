CREATE TABLE results (
    id SERIAL PRIMARY KEY,
    x INT,
    y INT,
    r INT,
    ishit BOOLEAN,
    executiontime VARCHAR(32),
    currenttime TIMESTAMP
);
