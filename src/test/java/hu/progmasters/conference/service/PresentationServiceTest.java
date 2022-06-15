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

    }


}
