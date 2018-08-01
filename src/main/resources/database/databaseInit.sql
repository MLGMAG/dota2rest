CREATE TABLE users (

id                        uuid            NOT NULL,
username                  VARCHAR(255)    NOT NULL UNIQUE,
email                     VARCHAR(255)    NOT NULL UNIQUE,
name                      VARCHAR(255)    NOT NULL,
password                  VARCHAR(255)    NOT NULL,

non_expired               boolean         NOT NULL,
non_locked                boolean         NOT NULL,
credentials_non_expired   boolean         NOT NULL,
enabled                   boolean         NOT NULL,


PRIMARY KEY(id)
);

CREATE TABLE user_authorities (

user_id                   uuid            NOT NULL,
authority_id              VARCHAR(255)    NOT NULL,

FOREIGN KEY(user_id)      REFERENCES users(id)
);

CREATE TABLE players (

steam_id32                INTEGER         NOT NULL,
name                      VARCHAR(255)    NOT NULL,
steam_id64                VARCHAR(255)    NOT NULL UNIQUE,
avatar                    VARCHAR(255)    NOT NULL,
steam_url                 VARCHAR(255)    NOT NULL,
solo_competitive_rank     VARCHAR(255),
competitive_rank          VARCHAR(255),

PRIMARY KEY(steam_id32)
);

CREATE TABLE user_players (

user_id                   uuid            NOT NULL,
player_id                 INTEGER         NOT NULL,

FOREIGN KEY(user_id)      REFERENCES users(id),
FOREIGN KEY(player_id)    REFERENCES players(steam_id32)
);

CREATE TABLE heroes (

id                        INTEGER         NOT NULL,
name                      VARCHAR(255)    NOT NULL UNIQUE,
icon_url                  VARCHAR(255)    NOT NULL UNIQUE,

PRIMARY KEY(id)
);

CREATE TABLE matches (

id                        BIGINT          NOT NULL,
radiant_win               boolean,
duration                  VARCHAR(255)    NOT NULL,
game_mode                 INTEGER         NOT NULL,
patch                     INTEGER,

PRIMARY KEY(id)
);

CREATE TABLE player_match (

player_id                 INTEGER         NOT NULL,
match_id                  BIGINT          NOT NULL,

FOREIGN KEY(player_id)    REFERENCES players(steam_id32),
FOREIGN KEY(match_id)     REFERENCES matches(id)
);