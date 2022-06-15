CREATE TABLE participation (
    id integer NOT NULL unique AUTO_INCREMENT,
    registration date,
    participant_id integer,
    presentation_id integer,
    PRIMARY KEY (id)
);

ALTER TABLE participation ADD CONSTRAINT fk_participant_participation FOREIGN KEY (participant_id) REFERENCES participant (id);

ALTER TABLE participation ADD CONSTRAINT fk_presentation_participation FOREIGN KEY (presentation_id) REFERENCES presentation (id);
