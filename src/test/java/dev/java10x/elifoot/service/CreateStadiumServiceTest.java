package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateStadiumRequest;
import dev.java10x.elifoot.entity.Stadium;
import dev.java10x.elifoot.mapper.StadiumMapper;
import dev.java10x.elifoot.repository.StadiumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateStadiumServiceTest {

    @InjectMocks
    CreateStadiumService createStadiumService;

    @Mock
    StadiumRepository stadiumRepository;
    @Mock
    StadiumMapper stadiumMapper;
    @Captor
    ArgumentCaptor<Stadium> stadiumCaptor;

    @Test
    @DisplayName("Should create a stadium successfully")
    void shouldCreateStadium() {
        CreateStadiumRequest request = CreateStadiumRequest.builder()
                .name("Test Stadium")
                .city("Test City")
                .capacity(50000)
                .urlImg("http://example.com/stadium.jpg")
                .build();

        Stadium stadium = Stadium.builder()
                .name(request.getName())
                .city(request.getCity())
                .capacity(request.getCapacity())
                .urlImg(request.getUrlImg())
                .build();

        Mockito.when(stadiumMapper.toEntity(request)).thenReturn(stadium);

        createStadiumService.execute(request);

        Mockito.verify(stadiumMapper).toEntity(request);
        Mockito.verify(stadiumRepository).save(Mockito.any());
        Mockito.verify(stadiumMapper).toResponse(Mockito.any());

        Mockito.verify(stadiumRepository).save(stadiumCaptor.capture());
        Stadium savedStadium = stadiumCaptor.getValue();
        assertNotNull(savedStadium);
    }
}