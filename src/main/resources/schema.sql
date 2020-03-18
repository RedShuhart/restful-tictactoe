DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS player;

DROP SEQUENCE IF EXISTS player_seq;
DROP SEQUENCE IF EXISTS game_seq;
DROP SEQUENCE IF EXISTS board_seq;


CREATE SEQUENCE IF NOT EXISTS player_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS game_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS board_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS players
(
    id         BIGINT DEFAULT player_seq.nextval NOT NULL PRIMARY KEY,
    tag        VARCHAR(255)                      NOT NULL,
    name       VARCHAR(255),
    created_at TIMESTAMP                         NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS games
(
    id         BIGINT DEFAULT game_seq.nextval NOT NULL PRIMARY KEY,
    tag        VARCHAR                         NOT NULL,
    player_x   BIGINT                          REFERENCES players (id) ON DELETE SET NULL,
    player_o   BIGINT                          REFERENCES players (id) ON DELETE SET NULL,
    state      SMALLINT                        NOT NULL,
    created_at TIMESTAMP                       NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS boards
(
    id         BIGINT       NOT NULL PRIMARY KEY REFERENCES games (id) ON DELETE CASCADE,
    placements VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
);