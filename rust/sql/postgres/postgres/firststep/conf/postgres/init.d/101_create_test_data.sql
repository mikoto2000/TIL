INSERT INTO "user" (id, name)
    values
        ('{d35696e8-5aa0-4b35-88cf-ded521b3dede}', 'user1')
        , ('{d35696e8-5aa0-4b35-88cf-ded521b3dedd}', 'user2')
        , ('{d35696e8-5aa0-4b35-88cf-ded521b3dedc}', 'user3')
        , ('{d35696e8-5aa0-4b35-88cf-ded521b3dedb}', 'user4')
        , ('{d35696e8-5aa0-4b35-88cf-ded521b3deda}', 'user5')
        ;

INSERT INTO "device" (id, name, user_id)
    values
    ('{d35696e8-5aa0-4b35-98cf-ded521b3dede}', 'device11', '{d35696e8-5aa0-4b35-88cf-ded521b3dede}')
    , ('{d35696e8-5aa0-4b35-98cf-ded521b3dedd}', 'device12', '{d35696e8-5aa0-4b35-88cf-ded521b3dede}')
    , ('{d35696e8-5aa0-4b35-98cf-ded521b3dedc}', 'device13', '{d35696e8-5aa0-4b35-88cf-ded521b3dede}')
    , ('{d35696e8-5aa0-4b35-98cf-ded521b3dedb}', 'device14', '{d35696e8-5aa0-4b35-88cf-ded521b3dede}')
    , ('{d35696e8-5aa0-4b35-98cf-ded521b3deda}', 'device15', '{d35696e8-5aa0-4b35-88cf-ded521b3dede}')
    , ('{d35696e8-5aa0-4b35-a8cf-ded521b3dede}', 'device21', '{d35696e8-5aa0-4b35-88cf-ded521b3dedd}')
    , ('{d35696e8-5aa0-4b35-a8cf-ded521b3dedd}', 'device22', '{d35696e8-5aa0-4b35-88cf-ded521b3dedd}')
    , ('{d35696e8-5aa0-4b35-a8cf-ded521b3dedc}', 'device23', '{d35696e8-5aa0-4b35-88cf-ded521b3dedd}')
    , ('{d35696e8-5aa0-4b35-a8cf-ded521b3dedb}', 'device24', '{d35696e8-5aa0-4b35-88cf-ded521b3dedd}')
    , ('{d35696e8-5aa0-4b35-a8cf-ded521b3deda}', 'device25', '{d35696e8-5aa0-4b35-88cf-ded521b3dedd}')
    ;
