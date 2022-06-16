create table lecturer
(
    id            int auto_increment
        primary key,
    name          varchar(255) null,
    date_of_birth date         null,
    institution   varchar(255) null,
    academic_rank varchar(255) null,
    email         varchar(255) null,
    constraint email
        unique (email),
    constraint id
        unique (id)
);



