package dev.java10x.elifoot.mapper;

import dev.java10x.elifoot.controller.request.CreateClubRequest;
import dev.java10x.elifoot.controller.response.ClubDetailResponse;
import dev.java10x.elifoot.controller.response.ClubResponse;
import dev.java10x.elifoot.entity.Club;
import dev.java10x.elifoot.entity.Stadium;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClubMapperTest {

    private final ClubMapper mapper = Mappers.getMapper(ClubMapper.class);

    @Test
    @DisplayName("Should map Club entity to ClubResponse")
    void toResponse() {
        // Given
        Club club = new Club();
        club.setId(1L);
        club.setName("São Paulo FC");
        club.setFounded(LocalDate.of(1935, 1, 1));
        club.setUrlImg("https://www.superstarsoccer.com.br/clubs/spfc.jpg");

        // When
        ClubResponse response = mapper.toResponse(club);

        // Then
        assertNotNull(response);
        assertEquals(club.getId(), response.getId());
        assertEquals(club.getName(), response.getName());
        assertEquals(club.getFounded(), response.getFounded());
        assertEquals(club.getUrlImg(), response.getUrlImg());
    }

    @Test
    @DisplayName("Should return null when Club entity is null")
    void toResponseNull() {
        // Given + When
        ClubResponse response = mapper.toResponse(null);

        // Then
        assertNull(response);
    }

    @Test
    @DisplayName("Should map Club entity to ClubDetailResponse")
    void toDetailResponse() {
        // Given
        Club club = new Club();
        club.setId(1L);
        club.setName("São Paulo FC");
        club.setFounded(LocalDate.of(1935, 1, 1));
        club.setUrlImg("https://www.superstarsoccer.com.br/clubs/spfc.jpg");
        club.setStadium(Stadium.builder()
                .id(5L)
                .name("Estádio do Morumbi")
                .city("Morumbi")
                .capacity(35000)
                .urlImg("http://www.superstarsoccer.com.br/clubs/spfc.jpg")
                .build());

        // When
        ClubDetailResponse response = mapper.toDetailResponse(club);

        // Then
        assertNotNull(response);
        assertEquals(club.getId(), response.getId());
        assertEquals(club.getName(), response.getName());
        assertEquals(club.getFounded(), response.getFounded());
        assertEquals(club.getUrlImg(), response.getUrlImg());
        assertEquals(club.getStadium().getId(), response.getStadium().getId());
        assertEquals(club.getStadium().getName(), response.getStadium().getName());
        assertEquals(club.getStadium().getCity(), response.getStadium().getCity());
        assertEquals(club.getStadium().getCapacity(), response.getStadium().getCapacity());
        assertEquals(club.getStadium().getUrlImg(), response.getStadium().getUrlImg());
    }

    @Test
    @DisplayName("Should map Club entity to ClubDetailResponse Without Stadium")
    void toDetailResponseWithoutStadium() {
        // Given
        Club club = new Club();
        club.setId(1L);
        club.setName("São Paulo FC");
        club.setFounded(LocalDate.of(1935, 1, 1));
        club.setUrlImg("https://www.superstarsoccer.com.br/clubs/spfc.jpg");

        // When
        ClubDetailResponse response = mapper.toDetailResponse(club);

        // Then
        assertNotNull(response);
        assertEquals(club.getId(), response.getId());
        assertEquals(club.getName(), response.getName());
        assertEquals(club.getFounded(), response.getFounded());
        assertEquals(club.getUrlImg(), response.getUrlImg());
        assertNull(response.getStadium());
    }

    @Test
    @DisplayName("Should return null when Club entity is null for ClubDetailResponse")
    void toDetailResponseNull() {
        // Given + When
        ClubDetailResponse response = mapper.toDetailResponse(null);

        // Then
        assertNull(response);
    }

    @Test
    @DisplayName("Should map CreateClubRequest to Club entity")
    void toEntity() {
        // Given
        CreateClubRequest request = new CreateClubRequest();
        request.setName("São Paulo FC");
        request.setFounded(LocalDate.of(1935, 1, 1));
        request.setStadiumId(1L);
        request.setUrlImg("https://www.superstarsoccer.com.br/clubs/spfc.jpg");

        // When
        Club club = mapper.toEntity(request);

        // Then
        assertNotNull(club);
        assertEquals(request.getName(), club.getName());
        assertEquals(request.getFounded(), club.getFounded());
        assertEquals(request.getStadiumId(), club.getStadium().getId());
        assertEquals(request.getUrlImg(), club.getUrlImg());
        assertNotNull(club.getStadium());
        assertEquals(request.getStadiumId(), club.getStadium().getId());
    }

    @Test
    @DisplayName("Should return null when CreateClubRequest is null")
    void toEntityNul() {
        // Given + When
        Club club = mapper.toEntity(null);

        // Then
        assertNull(club);
    }
}