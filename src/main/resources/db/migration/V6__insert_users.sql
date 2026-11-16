INSERT INTO app_user (username, password_hash)
VALUES
    ('user', '$2a$10$s77VLHukCNF71ogPdjWM7e3hGB/uKU5pe1jgtnKAIBP7FnGFLoG8i'),
    ('admin', '$2a$10$8BkRCKRqjrRa8iyxawTBHurExtKzS.BP2eZWYpCNPbgrN3Be79KCC');

INSERT INTO user_role (user_id, role)
SELECT id, 'USER'
FROM app_user;

INSERT INTO user_role (user_id, role)
SELECT id, 'ADMIN'
FROM app_user
WHERE username = 'admin';