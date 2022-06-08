CREATE TABLE lecturer (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    academic_rank varchar(255),
    institution varchar(255),
    date_of_birth date,
    email varchar(255) unique,

    PRIMARY KEY (id)
);

