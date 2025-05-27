package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreatePlayerRequest;
import dev.java10x.elifoot.entity.Club;
import dev.java10x.elifoot.entity.Player;
import dev.java10x.elifoot.entity.Position;
import dev.java10x.elifoot.mapper.PlayerMapper;
import dev.java10x.elifoot.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreatePlayerServiceTest {

    @InjectMocks
    CreatePlayerService createPlayerService;

    @Mock
    PlayerRepository playerRepository;
    @Mock
    PlayerMapper playerMapper;
    @Mock
    FindClubService findClubService;
    @Captor
    ArgumentCaptor<Player> playerCaptor;

    @Test
    @DisplayName("Should create a player successfully")
    void shouldCreatePlayer() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("Test Player")
                .position(Position.FORWARD)
                .shirtNumber(10)
                .urlImg("http://example.com/player.jpg")
                .clubId(1L)
                .build();

        Club club = Club.builder()
                .id(1L)
                .name("Club")
                .build();

        Player player = Player.builder()
                .id(1L)
                .name(request.getName())
                .position(request.getPosition())
                .shirtNumber(request.getShirtNumber())
                .urlImg(request.getUrlImg())
                .club(club)
                .build();

        Mockito.when(playerMapper.toEntity(Mockito.any())).thenReturn(player);
        Mockito.when(findClubService.findById(club.getId())).thenReturn(club);

        createPlayerService.execute(request);

        // Assert
        Mockito.verify(playerMapper).toEntity(Mockito.any());
        Mockito.verify(findClubService).findById(Mockito.anyLong());
        Mockito.verify(playerRepository).save(Mockito.any());
        Mockito.verify(playerMapper).toResponse(Mockito.any());

        Mockito.verify(playerRepository).save(playerCaptor.capture());
        Player savedPlayer = playerCaptor.getValue();
        assertNotNull(savedPlayer);
        assertEquals(player.getName(), savedPlayer.getName());
        assertEquals(player.getPosition(), savedPlayer.getPosition());
        assertEquals(player.getShirtNumber(), savedPlayer.getShirtNumber());
        assertEquals(player.getUrlImg(), savedPlayer.getUrlImg());
    }
}