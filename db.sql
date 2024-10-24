CREATE TYPE role_type as enum('CLIENT','ADMIN');
CREATE TYPE ticket_type as enum('BUS','CONCERT','NOT_DEFINED');

CREATE TABLE usr(
                    id uuid NOT NULL UNIQUE,
                    name varchar(40),
                    creation_date timestamp,
                    role role_type,
                    PRIMARY KEY(id)
);

CREATE TABLE ticket(
                       id uuid NOT NULL UNIQUE,
                       user_id uuid,
                       ticket_type ticket_type,
                       creation_date timestamp,
                       PRIMARY KEY (id),
                       CONSTRAINT fk_usr
                           FOREIGN KEY (user_id)
                               REFERENCES usr(id)
                               ON UPDATE CASCADE
                               ON DELETE CASCADE
);