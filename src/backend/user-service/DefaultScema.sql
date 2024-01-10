CREATE TABLE users (
    id          INT IDENTITY(1, 1) NOT NULL,
    email       VARCHAR(50)         NOT NULL,
    password    VARCHAR(50)         NOT NULL,
    name        VARCHAR(20)         NOT NULL,
    profile     VARCHAR(255)        NULL,
    created_at  DATETIME            NOT NULL DEFAULT GETDATE(),
    updated_at  DATETIME            NOT NULL DEFAULT GETDATE(),
    is_deleted  BIT                 NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX email_index (email)
);

CREATE TABLE friends (
    id                  int IDENTITY(1, 1)  NOT NULL,
    first_user_id       int                 NOT NULL,
    second_user_id      int                 NOT NULL,
    created_at          DATETIME            NOT NULL DEFAULT GETDATE(),
    PRIMARY     KEY (id),
    FOREIGN     KEY (first_user_id)  REFERENCES users(id),
    FOREIGN     KEY (second_user_id) REFERENCES users(id)
);