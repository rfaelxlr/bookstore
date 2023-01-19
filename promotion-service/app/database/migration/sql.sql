create table promotion(
	id serial primary key,
	external_id uuid not null,
	code varchar(10) not null,
	discount float,
	active boolean,
	CONSTRAINT code_unique UNIQUE (code)
);

create INDEX IF NOT EXISTS  promotion_code_idx on promotion (code);