ALTER TABLE book
    ADD COLUMN IF NOT EXISTS owner_id BIGINT;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'fk_book_owner'
          AND conrelid = 'public.book'::regclass
    ) THEN
ALTER TABLE book
    ADD CONSTRAINT fk_book_owner
        FOREIGN KEY (owner_id)
            REFERENCES app_user(id);
END IF;
END $$;