INSERT INTO "user" (id, username, password)
SELECT '1867e482-a0ab-11ee-8c90-0242ac120002', 'User 1', 'TestPassword1234'
    WHERE NOT EXISTS (
    SELECT 1 FROM "user" WHERE username = 'User 1'
);

INSERT INTO "user" (id, username, password)
SELECT '7942010a-3e2f-484e-8b04-41f0adb8d9e6', 'admin', 'admin'
    WHERE NOT EXISTS (
    SELECT 1 FROM "user" WHERE username = 'admin'
);
