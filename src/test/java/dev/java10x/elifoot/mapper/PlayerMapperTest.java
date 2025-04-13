package dev.java10x.elifoot.mapper;

import dev.java10x.elifoot.controller.request.CreatePlayerRequest;
import dev.java10x.elifoot.controller.response.PlayerDetailResponse;
import dev.java10x.elifoot.controller.response.PlayerResponse;
import dev.java10x.elifoot.entity.Club;
import dev.java10x.elifoot.entity.Player;
import dev.java10x.elifoot.entity.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerMapperTest {

    private final PlayerMapper mapper = Mappers.getMapper(PlayerMapper.class);

    @Test
    @DisplayName("Should convert CreatePlayerRequest to Player")
    void toEntity() {
        // Given
        CreatePlayerRequest request = new CreatePlayerRequest();
        request.setName("Lionel Messi");
        request.setPosition(Position.FORWARD);
        request.setShirtNumber(10);
        request.setClubId(1L);
        request.setUrlImg("https://www.superstarsoccer.com.br/players/messi.jpg");

        // When
        Player player = mapper.toEntity(request);

        // Then
        assertNotNull(player);
        assertEquals(request.getName(), player.getName());
        assertEquals(request.getPosition(), player.getPosition());
        assertEquals(request.getClubId(), player.getClub().getId());
        assertEquals(request.getUrlImg(), player.getUrlImg());
        assertEquals(request.getShirtNumber(), player.getShirtNumber());
    }

    @Test
    @DisplayName("Should convert Player to PlayerResponse")
    void toResponse() {
        // Given
        Player player = new Player();
        player.setId(1L);
        player.setName("Lionel Messi");
        player.setPosition(Position.FORWARD);
        player.setShirtNumber(10);
        player.setUrlImg("https://www.superstarsoccer.com.br/players/messi.jpg");

        // When
        PlayerResponse response = mapper.toResponse(player);

        // Then
        assertNotNull(response);
        assertEquals(player.getId(), response.getId());
        assertEquals(player.getName(), response.getName());
        assertEquals(player.getPosition().getLabel(), response.getPosition());
        assertEquals(player.getShirtNumber(), response.getShirtNumber());
        assertEquals(player.getUrlImg(), response.getUrlImg());
    }

    @Test
    @DisplayName("Should convert Player to PlayerDetailResponse")
    void toDetailResponse() {
        // Given
        Player player = new Player();
        player.setId(1L);
        player.setName("Lionel Messi");
        player.setPosition(Position.FORWARD);
        player.setShirtNumber(10);
        player.setUrlImg("https://www.superstarsoccer.com.br/players/messi.jpg");
        player.setClub(Club.builder().id(2L).name("Seleção Argentina").build());

        // When
        PlayerDetailResponse response = mapper.toDetailResponse(player);

        // Then
        assertNotNull(response);
        assertEquals(player.getId(), response.getId());
        assertEquals(player.getName(), response.getName());
        assertEquals(player.getPosition().getLabel(), response.getPosition());
        assertEquals(player.getShirtNumber(), response.getShirtNumber());
        assertEquals(player.getUrlImg(), response.getUrlImg());
        assertEquals(player.getClub().getId(), response.getClub().getId());
        assertEquals(player.getClub().getName(), response.getClub().getName());
    }
}