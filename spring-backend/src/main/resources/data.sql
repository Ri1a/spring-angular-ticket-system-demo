CREATE TABLE IF NOT EXISTS "authority" (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS "user_authority" (
    user_id UUID NOT NULL,
    authority_id UUID NOT NULL,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id),
    FOREIGN KEY (authority_id) REFERENCES "authority"(id)
    );


CREATE TABLE IF NOT EXISTS "project" (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Insert data into authority table if not exists
INSERT INTO authority (id, name)
SELECT 'authority-id-1', 'ROLE_USER'
    WHERE NOT EXISTS (
    SELECT 1 FROM authority WHERE id = 'authority-id-1'
);

INSERT INTO authority (id, name)
SELECT 'authority-id-2', 'ROLE_ADMIN'
    WHERE NOT EXISTS (
    SELECT 1 FROM authority WHERE id = 'authority-id-2'
);

-- Insert user with a check to avoid duplicates
INSERT INTO "user" (id, username, password)
SELECT '1867e482-a0ab-11ee-8c90-0242ac120002', 'User 1', 'TestPassword1234'
    WHERE NOT EXISTS (
    SELECT 1 FROM "user" WHERE username = 'User 1'
);

-- Associate user with ROLE_USER authority
INSERT INTO user_authority (user_id, authority_id)
SELECT '1867e482-a0ab-11ee-8c90-0242ac120002', 'authority-id-1'
    WHERE NOT EXISTS (
    SELECT 1 FROM user_authority WHERE user_id = '1867e482-a0ab-11ee-8c90-0242ac120002'
);

-- Insert the admin user (if not exists)
INSERT INTO "user" (id, username, password)
SELECT '7942010a-3e2f-484e-8b04-41f0adb8d9e6', 'admin', 'admin'
    WHERE NOT EXISTS (
    SELECT 1 FROM "user" WHERE username = 'admin'
);

-- Associate the admin user with ROLE_ADMIN authority (if not exists)
INSERT INTO user_authority (user_id, authority_id)
SELECT '7942010a-3e2f-484e-8b04-41f0adb8d9e6', 'authority-id-2'
    WHERE NOT EXISTS (
    SELECT 1 FROM user_authority WHERE user_id = '7942010a-3e2f-484e-8b04-41f0adb8d9e6'
);

-- Insert a new project and associate it with the admin user (if not exists)
INSERT INTO "project" (id, user_id)
SELECT 'e5e6da46-1bb6-4f64-b765-0a67aab375d9', '7942010a-3e2f-484e-8b04-41f0adb8d9e6'
    WHERE NOT EXISTS (
    SELECT 1 FROM "project" WHERE user_id = '7942010a-3e2f-484e-8b04-41f0adb8d9e6'
);
