CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid()
    , name text NOT NULL
);

CREATE TABLE "device" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid()
    , name text NOT NULL
    , user_id UUID REFERENCES "user"(id)
);
