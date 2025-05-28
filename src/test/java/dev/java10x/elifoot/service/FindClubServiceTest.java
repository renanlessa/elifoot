package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.response.ClubResponse;
import dev.java10x.elifoot.controller.response.PlayerResponse;
import dev.java10x.elifoot.entity.Club;
import dev.java10x.elifoot.exception.ResourceNotFoundException;
import dev.java10x.elifoot.mapper.ClubMapper;
import dev.java10x.elifoot.repository.ClubRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindClubServiceTest {

    @InjectMocks
    private FindClubService findClubService;

    @Mock
    private FindPlayerService findPlayerService;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private ClubMapper clubMapper;

    @Test
    @DisplayName("Should find all clubs with pagination")
    void shouldFindAllClubsWithPagination() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Club club1 = Club.builder()
                .id(1L)
                .name("Club 1")
                .founded(LocalDate.of(1980, 1, 1))
                .urlImg("url1")
                .build();
        Club club2 = Club.builder()
                .id(2L)
                .name("Club 2")
                .founded(LocalDate.of(1990, 2, 2))
                .urlImg("url2")
                .build();
        List<Club> clubs = Arrays.asList(club1, club2);
        Page<Club> clubPage = new PageImpl<>(clubs, pageable, clubs.size());

        ClubResponse clubResponse1 = new ClubResponse(1L, "Club 1", LocalDate.of(1980, 1, 1), "url1");
        ClubResponse clubResponse2 = new ClubResponse(2L, "Club 2", LocalDate.of(1990, 2, 2), "url2");

        when(clubRepository.findAll(any(Pageable.class))).thenReturn(clubPage);
        when(clubMapper.toResponse(club1)).thenReturn(clubResponse1);
        when(clubMapper.toResponse(club2)).thenReturn(clubResponse2);

        // When
        Page<ClubResponse> result = findClubService.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(clubRepository).findAll(pageable);
        verify(clubMapper, times(2)).toResponse(any(Club.class));
    }

    @Test
    @DisplayName("Should find club by id successfully")
    void shouldFindClubById() {
        // Given
        Long clubId = 1L;
        Club club = Club.builder()
                .id(clubId)
                .name("Club 1")
                .founded(LocalDate.of(1980, 1, 1))
                .urlImg("url1")
                .build();

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        // When
        Club result = findClubService.findById(clubId);

        // Then
        assertNotNull(result);
        assertEquals(clubId, result.getId());
        assertEquals("Club 1", result.getName());
        verify(clubRepository).findById(clubId);
    }

    @Test
    @DisplayName("Should throw exception when club not found by id")
    void shouldThrowExceptionWhenClubNotFoundById() {
        // Given
        Long clubId = 1L;
        when(clubRepository.findById(clubId)).thenReturn(Optional.empty());

        // When/Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> findClubService.findById(clubId)
        );

        assertEquals("Club not found for id: " + clubId, exception.getMessage());
        verify(clubRepository).findById(clubId);
    }

    @Test
    @DisplayName("Should find players by club id")
    void shouldFindPlayersByClubId() {
        // Given
        Long clubId = 1L;
        PlayerResponse player1 = new PlayerResponse(1L, "Player 1", "FORWARD", 10, "url");
        PlayerResponse player2 = new PlayerResponse(2L, "Player 2", "FORWARD", 11, "url");
        List<PlayerResponse> players = Arrays.asList(player1, player2);

        when(findPlayerService.findByClubId(clubId)).thenReturn(players);

        // When
        List<PlayerResponse> result = findClubService.findByClubId(clubId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(findPlayerService).findByClubId(clubId);
    }
}
