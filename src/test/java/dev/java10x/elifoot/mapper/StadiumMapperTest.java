package dev.java10x.elifoot.mapper;

import dev.java10x.elifoot.controller.request.CreateStadiumRequest;
import dev.java10x.elifoot.controller.response.StadiumResponse;
import dev.java10x.elifoot.entity.Stadium;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class StadiumMapperTest {

    private final StadiumMapper mapper = Mappers.getMapper(StadiumMapper.class);

    @Test
    @DisplayName("Should convert CreateStadiumRequest to Stadium")
    void toEntity() {
        // Given
        CreateStadiumRequest request = new CreateStadiumRequest();
        request.setCity("São Paulo");
        request.setName("Estádio do Morumbi");
        request.setCapacity(60000);
        request.setUrlImg("https://www.superstarsoccer.com.br/stadiums/morumbi.jpg");

        // When
        Stadium stadium = mapper.toEntity(request);

        // Then
        assertNotNull(stadium);
        assertEquals(request.getCity(), stadium.getCity());
        assertEquals(request.getName(), stadium.getName());
        assertEquals(request.getCapacity(), stadium.getCapacity());
        assertEquals(request.getUrlImg(), stadium.getUrlImg());
    }

    @Test
    @DisplayName("Should return null when CreateStadiumRequest is null")
    void toEntityNull() {
        // Given + When
        Stadium stadium = mapper.toEntity(null);

        // Then
        assertNull(stadium);
    }

    @Test
    @DisplayName("Should convert Stadium to StadiumResponse")
    void toResponse() {
        // Given
        Stadium stadium = new Stadium();
        stadium.setId(1L);
        stadium.setCity("São Paulo");
        stadium.setName("Estádio do Morumbi");
        stadium.setCapacity(60000);
        stadium.setUrlImg("https://www.superstarsoccer.com.br/stadiums/morumbi.jpg");

        // When
        StadiumResponse response = mapper.toResponse(stadium);

        // Then
        assertNotNull(response);
        assertEquals(stadium.getId(), response.getId());
        assertEquals(stadium.getCity(), response.getCity());
        assertEquals(stadium.getName(), response.getName());
        assertEquals(stadium.getCapacity(), response.getCapacity());
        assertEquals(stadium.getUrlImg(), response.getUrlImg());
    }

    @Test
    @DisplayName("Should return null when Stadium is null")
    void toResponseNull() {
        // Given + When
        StadiumResponse response = mapper.toResponse(null);

        // Then
        assertNull(response);
    }
}