BEGIN;

SET
client_encoding = 'LATIN1';

CREATE TABLE users
(
    id          integer      NOT NULL,
    username    varchar(255) NOT NULL,
    password    varchar(255) NOT NULL,
    email       varchar(255) NOT NULL,
    createdAt   varchar(255) NOT NULL,
    updatedAt   varchar(255) NOT NULL,
    active      varchar(255) NOT NULL,
    userProfile varchar(255) NOT NULL,
    roles       varchar(255) NOT NULL
);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

COMMIT;

ANALYZE
users;

