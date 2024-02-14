CREATE TABLE IF NOT EXISTS Users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email         TEXT UNIQUE NOT NULL,
    password      TEXT        NOT NULL,
    nickname      TEXT        NOT NULL,
    role          TEXT        NOT NULL,
    refresh_token TEXT
);

CREATE INDEX ix_email ON Users (email);

CREATE TABLE IF NOT EXISTS Carriers
(
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name  TEXT NOT NULL,
    phone TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Routes
(
    id                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    departure            TEXT    NOT NULL,
    arrival              TEXT    NOT NULL,
    carrier_id           UUID,
    trip_time_in_minutes INTEGER NOT NULL,

    CONSTRAINT carriers_fk FOREIGN KEY (carrier_id)
        REFERENCES Carriers (id)
);

CREATE TABLE IF NOT EXISTS Tickets
(
    id           UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    route_id     UUID,
    date         TIMESTAMP NOT NULL,
    seat         TEXT      NOT NULL,
    price        FLOAT     NOT NULL,
    currency     TEXT      NOT NULL,
    is_available BOOLEAN   NOT NULL DEFAULT TRUE,

    CONSTRAINT routes_fk FOREIGN KEY (route_id)
        REFERENCES Routes (id)
);

CREATE TABLE IF NOT EXISTS Users_Tickets
(
    user_id   UUID,
    ticket_id UUID,

    CONSTRAINT users_fk FOREIGN KEY (user_id)
        REFERENCES Users (id),
    CONSTRAINT tickets_fk FOREIGN KEY (ticket_id)
        REFERENCES Tickets (id)
);

