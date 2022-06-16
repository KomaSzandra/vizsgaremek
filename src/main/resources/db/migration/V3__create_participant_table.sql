create table participant
(
    id            int auto_increment
        primary key,
    name          varchar(255) null,
    date_of_birth date         null,
    academic_rank varchar(255) null,
    institution   varchar(255) null,
    email         varchar(255) null,
    constraint uq_email
        unique (email),
    constraint id_participant
        unique (id)
);
