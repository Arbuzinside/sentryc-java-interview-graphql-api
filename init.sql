DROP DATABASE IF EXISTS sentryc_interview;
CREATE DATABASE sentryc_interview;

-- Switch to the newly created database
\c sentryc_interview;

-- Drop tables if they exist
DROP TABLE IF EXISTS sellers;
DROP TABLE IF EXISTS seller_infos;
DROP TABLE IF EXISTS marketplaces;
DROP TABLE IF EXISTS producers;

-- Create tables
create table producers
(
    id         uuid      not null
        constraint "producersPK"
            primary key,
    name       varchar(255),
    created_at timestamp not null
);

create table marketplaces
(
    id         varchar(255) not null
        constraint "marketplacesPK"
            primary key,
    description varchar(255)
);

create table seller_infos
(
    id                    uuid          not null
        constraint "seller_infosPK"
            primary key,
    marketplace_id        varchar(255)
        constraint "FKr8ekbqgwa3g0uhgbaa1tchf09"
            references marketplaces,
    name                  varchar(2048) not null,
    url                   varchar(2048),
    country               varchar(255),
    external_id           varchar(255),
    constraint "UK12xaxk0c1mwxr3ovycs1qxtbk"
        unique (marketplace_id, external_id)
);

create table sellers
(
    id               uuid      not null
        constraint "marketplace_sellersPK"
            primary key,
    producer_id      uuid      not null
        constraint "FK6y70nxr3lhubusfq6ub427ien"
            references producers,
    seller_info_id   uuid
        constraint "FKp2fkfcqcndx9x9xkhk5va3cq4"
            references seller_infos,
    state            varchar(255) default 'REGULAR'::character varying not null
);
