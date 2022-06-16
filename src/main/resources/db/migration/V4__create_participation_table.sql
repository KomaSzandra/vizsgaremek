create table participation
(
    id              int auto_increment
        primary key,
    registration    date null,
    participant_id  int  null,
    presentation_id int  null,
    constraint uq_participation_id
        unique (id),
    constraint fk_participant_participation
        foreign key (participant_id) references participant (id),
    constraint fk_presentation_participation
        foreign key (presentation_id) references presentation (id)
);