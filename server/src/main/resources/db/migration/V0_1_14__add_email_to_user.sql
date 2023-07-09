ALTER TABLE "user"
    ADD COLUMN email VARCHAR NOT NULL DEFAULT '';

UPDATE "user" u
SET email = (SELECT username
             FROM user_credential uc
             WHERE uc.user_id = u.id);
