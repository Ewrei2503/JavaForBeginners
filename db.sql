CREATE DATABASE my_ticket_service_db;

CREATE TABLE usr(
                    id uuid NOT NULL UNIQUE,
                    name varchar(40),
                    creation_date timestamp,
                    PRIMARY KEY(id)
);

CREATE TYPE ticket_type as enum('bus','concert');

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