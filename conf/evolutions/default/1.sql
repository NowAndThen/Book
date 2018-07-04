# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table author (
  author                        varchar(255) not null,
  constraint pk_author primary key (author)
);

create table book (
  id                            integer not null,
  title                         varchar(255),
  year                          integer,
  constraint pk_book primary key (id)
);
create sequence book_seq;

create table book_to_author (
  id                            integer not null,
  id_book                       integer,
  author                        varchar(255),
  constraint pk_book_to_author primary key (id)
);
create sequence book_to_author_seq;


# --- !Downs

drop table if exists author;

drop table if exists book;
drop sequence if exists book_seq;

drop table if exists book_to_author;
drop sequence if exists book_to_author_seq;

