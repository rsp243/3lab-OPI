CREATE TABLE results (
    id SERIAL PRIMARY KEY,
    x NUMERIC,
    y NUMERIC,
    r NUMERIC,
    ishit BOOLEAN,
    executiontime VARCHAR(32),
    currenttime TIMESTAMP
);
