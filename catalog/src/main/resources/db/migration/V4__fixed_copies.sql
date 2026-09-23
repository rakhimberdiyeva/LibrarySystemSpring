ALTER TABLE books RENAME COLUMN copies TO copies_total;
ALTER TABLE books ADD COLUMN copies_available INTEGER;