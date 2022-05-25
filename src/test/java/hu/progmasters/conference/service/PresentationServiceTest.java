package hu.progmasters.conference.service;

import hu.progmasters.conference.domain.Presentation;
import hu.progmasters.conference.repository.PresentationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
@ExtendWith(MockitoExtension.class)
public class PresentationServiceTest {

    @Mock
    PresentationRepository presentationRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    PresentationService presentationService;

    private Presentation presentation;
    private Presentation presentation1;
    private Presentation presentation2;

    @BeforeEach
    void init() {
//        testConference = new Conference(1, "Budapest, Conference Street", "New Year Conference", LocalDate.of(2023, Month.JANUARY, 1), List.of(new Presentation(1, new Lecturer(), "Bob's presentation", LocalDateTime.of(2023, Month.JANUARY, 1, 8, 0,0)), new Presentation(2, new Lecturer(2, "John Lecturer", "CEU"), "John's Presentation", LocalDateTime.of(2023, Month.JANUARY, 1, 9, 0,0))));
//
//        testConference1 = new Conference(2, "Szeged Conference Street", "Ultimate Conference", LocalDate.of(2023, Month.JANUARY, 20), List.of(new Presentation(3, new Lecturer(3, "Jonathan Lecturer", "SZTE"), "Jonathan's presentation", LocalDateTime.of(2023, Month.JANUARY, 20, 9, 0, 0)), new Presentation(4, new Lecturer(4, "Christian Lecturer", "BME"), "Christian's presentation", LocalDateTime.of(2023, Month.JANUARY, 20, 12, 0, 0))));
//
//        testConference2 = new Conference();


    }


}
