CREATE TABLE presentation (
    id integer NOT NULL AUTO_INCREMENT,
    lecturer_id integer,
    title varchar(255) unique,
    start_time datetime,
    PRIMARY KEY (id)
);


