ALTER TABLE JOB ADD COLUMN STATUS VARCHAR(16) NOT NULL;
ALTER TABLE JOB ADD COLUMN ERRORS_COUNT INTEGER NOT NULL DEFAULT 0;