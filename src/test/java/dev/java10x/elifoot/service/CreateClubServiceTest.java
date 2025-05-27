package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateClubRequest;
import dev.java10x.elifoot.entity.Club;
import dev.java10x.elifoot.entity.Stadium;
import dev.java10x.elifoot.mapper.ClubMapper;
import dev.java10x.elifoot.repository.ClubRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateClubServiceTest {

    @InjectMocks
    CreateClubService createClubService;
    @Mock
    ClubRepository clubRepository;
    @Mock
    FindStadiumService findStadiumService;
    @Mock
    ClubMapper clubMapper;
    @Captor
    ArgumentCaptor<Club> clubCaptor;

    @Test
    @DisplayName("Should save new club with stadium successfully")
    void shouldSaveNewClubWithStadium() {
        // Given
        Stadium stadium = Stadium.builder()
                .id(2L)
                .name("New Stadium")
                .city("New City")
                .capacity(2900)
                .urlImg("url_imagem")
                .build();

        Club newClub = Club.builder()
                .id(1L)
                .name("New Club")
                .founded(LocalDate.of(1980, 10, 10))
                .urlImg("url_imagem")
                .stadium(Stadium.builder().id(2L).build())
                .build();

        Mockito.when(clubMapper.toEntity(Mockito.any())).thenReturn(newClub);
        Mockito.when(findStadiumService.findById(Mockito.anyLong())).thenReturn(stadium);

        // When
        createClubService.execute(Mockito.any(CreateClubRequest.class));

        // Then
        Mockito.verify(clubMapper).toEntity(Mockito.any());
        Mockito.verify(findStadiumService).findById(Mockito.anyLong());
        Mockito.verify(clubMapper).toResponse(Mockito.any());
        Mockito.verify(clubRepository).save(clubCaptor.capture());
        Club club = clubCaptor.getValue();
        assertNotNull(club);
        assertTrue(club.isActive());
        assertNotNull(club.getCreatedAt());
        assertNotNull(club.getStadium());
        assertEquals(newClub.getName(), club.getName());
        assertEquals(newClub.getFounded(), club.getFounded());
        assertEquals(newClub.getUrlImg(), club.getUrlImg());
        assertEquals(newClub.getStadium(), club.getStadium());
    }

    @Test
    @DisplayName("Should save new club without stadium successfully")
    void shouldSaveNewClubWithoutStadium() {
        // Given
        Club newClub = Club.builder()
                .id(1L)
                .name("New Club")
                .founded(LocalDate.of(1980, 10, 10))
                .urlImg("url_imagem")
                .build();

        Mockito.when(clubMapper.toEntity(Mockito.any())).thenReturn(newClub);

        // When
        createClubService.execute(Mockito.any(CreateClubRequest.class));

        // Then
        Mockito.verify(clubMapper).toEntity(Mockito.any());
        Mockito.verify(findStadiumService, Mockito.never()).findById(Mockito.anyLong());
        Mockito.verify(clubMapper).toResponse(Mockito.any());
        Mockito.verify(clubRepository).save(clubCaptor.capture());
        Club club = clubCaptor.getValue();
        assertNotNull(club);
        assertTrue(club.isActive());
        assertNotNull(club.getCreatedAt());
        assertNull(club.getStadium());
        assertEquals(newClub.getName(), club.getName());
        assertEquals(newClub.getFounded(), club.getFounded());
        assertEquals(newClub.getUrlImg(), club.getUrlImg());
        assertEquals(newClub.getStadium(), club.getStadium());
    }
}