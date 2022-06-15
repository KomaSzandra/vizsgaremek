CREATE TABLE presentation (
    id integer NOT NULL unique AUTO_INCREMENT,
    lecturer_id integer,
    title varchar(255) unique,
    start_time datetime,
    PRIMARY KEY (id)
);

ALTER TABLE presentation ADD CONSTRAINT fk_presentation_lecturer FOREIGN KEY (lecturer_id) REFERENCES lecturer (id);

