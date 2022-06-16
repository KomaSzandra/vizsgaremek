create table presentation
(
    id          int auto_increment
        primary key,
    lecturer_id int          null,
    title       varchar(255) null,
    start_time  datetime     null,
    constraint id_presentation
        unique (id),
    constraint title
        unique (title),
    constraint fk_presentation_lecturer
        foreign key (lecturer_id) references lecturer (id)
);
