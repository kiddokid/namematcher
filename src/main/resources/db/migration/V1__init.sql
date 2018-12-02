CREATE SCHEMA IF NOT  EXISTS namematching;
create sequence namematching.risk_customers_id_seq
;

create sequence namematching.vkusers_id_seq
;

create table if not exists namematching.risk_customers
(
	id serial not null
		constraint risk_customers_pkey
			primary key,
	email varchar(255),
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	middlename varchar(255),
	address1 varchar(255) not null,
	address2 varchar(255),
	regionstate varchar(255),
	city varchar(255) not null,
	zip varchar(25),
	country varchar(50) not null,
	reason text,
	comment text,
	timestamp timestamp default now(),
	rp_added_by_id bigint
)
;

create table if not exists risk_persons
(
	id bigserial not null
		constraint risk_persons_pkey
			primary key,
	address1 varchar(255) not null,
	address2 varchar(255),
	city varchar(255),
	rp_added_by_id bigint,
	comment varchar(255),
	country varchar(255) not null,
	email varchar(255) not null,
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	middlename varchar(255),
	reason varchar(255) not null,
	regionstate varchar(255),
	timestamp timestamp,
	zip varchar(255)
)
;

create table if not exists namematching.vkuser
(
	id serial not null
		constraint vkusers_pkey
			primary key,
	riskcustomerid bigint not null,
	possibleids bigint[],
	preferableid bigint,
	totalfriendscount integer,
	friends bigint[],
	date_created timestamp with time zone default now() not null,
	date_modified timestamp with time zone default now() not null
)
;

create index if not exists vkusers_idx_possibleids
	on namematching.vkuser (possibleids)
;

create table if not exists vk_user
(
	id bigserial not null
		constraint vk_user_pkey
			primary key,
	date_created timestamp,
	date_modified timestamp,
	friends bytea,
	possibleids bytea,
	preferableid bigint,
	riskcustomerid bigint,
	totalfriendscount bigint
)
;

