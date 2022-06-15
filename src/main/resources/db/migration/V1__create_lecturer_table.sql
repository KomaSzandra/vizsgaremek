CREATE TABLE lecturer (
    id integer NOT NULL unique AUTO_INCREMENT,
    name varchar(255),
    date_of_birth date,
    institution varchar(255),
    academic_rank varchar(255),
    email varchar(255) unique,

    PRIMARY KEY (id)
);



