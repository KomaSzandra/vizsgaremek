CREATE TABLE participant (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    academic_rank varchar(255),
    date_of_birth date,
    institution varchar(255),
    email varchar(255),
    presentation_id integer,
    PRIMARY KEY (id)
);

ALTER TABLE participant ADD CONSTRAINT fk_presentation_participant FOREIGN KEY (presentation_id) REFERENCES presentation (id);