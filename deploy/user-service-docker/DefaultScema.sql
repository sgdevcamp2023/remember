USE UserDB;

CREATE TABLE users (
    id          BIGINT IDENTITY(1, 1) NOT NULL,
    email       VARCHAR(50)         NOT NULL,
    password    VARCHAR(64)         NOT NULL,
    name        VARCHAR(20)         NOT NULL,
    profile     VARCHAR(255)        NULL,
    created_at  DATETIME            NOT NULL DEFAULT GETDATE(),
    updated_at  DATETIME            NOT NULL DEFAULT GETDATE(),
    is_deleted  BIT                 NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX email_index (email)
);

CREATE TABLE friends (
    id                  BIGINT IDENTITY(1, 1)  NOT NULL,
    first_user_id       BIGINT                 NOT NULL,
    second_user_id      BIGINT                 NOT NULL,
    created_at          DATETIME            NOT NULL DEFAULT GETDATE(),
    PRIMARY     KEY (id),
    FOREIGN     KEY (first_user_id)  REFERENCES users(id),
    FOREIGN     KEY (second_user_id) REFERENCES users(id)
);

-- SELECT * FROM users;
-- DROP TABLE friends, users;

-- INSERT INTO users (email, password, name, profile) VALUES ('asdfae@aasdfa213.com', 'asdfasdf', 'asdfasdf', 'asdfasdf');
-- INSERT INTO users (email, password, name, profile) VALUES ('afdc234@aasdfa213.com', 'asdfasdf', 'asdfasdf', 'asdfasdf');
-- INSERT INTO users (email, password, name, profile) VALUES ('dignrnfjrl12@aasdfa213.com', 'asdfasdf', 'asdfasdf', 'asdfasdf');
-- INSERT INTO users (email, password, name, profile) VALUES ('dicunnekr@aasdfa213.com', 'asdfasdf', 'asdfasdf', 'asdfasdf');
-- INSERT INTO users (email, password, name, profile) VALUES ('naruto1234@aasdfa213.com', 'asdfasdf', 'asdfasdf', 'asdfasdf');
-- INSERT INTO users (email, password, name, profile) VALUES ('duucnkf@aasdfa213.com', 'asdfasdf', 'asdfasdf', 'asdfasdf');

-- SELECT * FROM users;

-- SELECT * FROM users WHERE id IN (SELECT first_user_id FROM friends WHERE second_user_id = 1 UNION SELECT second_user_id FROM friends WHERE first_user_id = 1);

-- SELECT * FROM friends WHERE (first_user_id = 1 AND second_user_id = 1) OR (first_user_id = 1 AND second_user_id = 1);