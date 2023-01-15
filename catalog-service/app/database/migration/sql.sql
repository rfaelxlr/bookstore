create table if not exists product (
	id serial primary key,
	external_id uuid not null,
	code varchar(10) not null,
	name varchar(100) not null,
	description varchar(150),
	image_url varchar(200),
	price float not null,
	discount float,
	active boolean,
	CONSTRAINT code_unique UNIQUE (code)
);

create INDEX IF NOT EXISTS  product_code_idx on product (code);

create INDEX IF NOT EXISTS  product_name_description_idx on product (name, description);