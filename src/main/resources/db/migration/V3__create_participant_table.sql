CREATE TABLE participant (
    id integer NOT NULL unique AUTO_INCREMENT,
    name varchar(255),
    date_of_birth date,
    academic_rank varchar(255),
    institution varchar(255),
    email varchar(255) unique,
    PRIMARY KEY (id)
);
