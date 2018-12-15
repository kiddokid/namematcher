CREATE SCHEMA if not exists namematching;
create sequence  namematching.risk_customers_id_seq
;

create sequence if not exists  namematching.vkusers_id_seq
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

create table if not exists  namematching.vkuser
(
	id serial not null
		constraint vkusers_pkey
			primary key,
	riskcustomerid bigint not null,
	possibleids INTEGER[],
	preferableid INTEGER ,
	totalfriendscount integer,
	friends INTEGER[],
	date_created timestamp with time zone default now() not null,
	date_modified timestamp with time zone default now() not null
)
;

create table namematching.vk_user_friends
(
  vk_user_id bigint not null
    constraint fkb727emvejkt2hs30qbtfjywu
    references vkuser,
  friends    integer
);

create table namematching.vk_user_possibleids
(
  vk_user_id  bigint not null
    constraint fksbpjq8mpnprhmbsr6kj03deq6
    references vkuser,
  possibleids integer
);


create index if not exists  vkusers_idx_possibleids
	on namematching.vkuser (possibleids)
;
