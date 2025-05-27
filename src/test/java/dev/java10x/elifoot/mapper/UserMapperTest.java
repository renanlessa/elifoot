package dev.java10x.elifoot.mapper;

import dev.java10x.elifoot.controller.request.CreateUserRequest;
import dev.java10x.elifoot.controller.response.UserResponse;
import dev.java10x.elifoot.entity.Scope;
import dev.java10x.elifoot.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("Should map CreateUserRequest to User entity")
    void toEntity() {
        // Given
        CreateUserRequest request = new CreateUserRequest();
        request.setName("new user");
        request.setEmail("newuser@java10x.com");
        request.setPassword("password");
        request.setScopes(List.of(34L, 12L));

        // When
        User user = mapper.toEntity(request);

        // Then
        assertNotNull(user);
        assertEquals(request.getName(), user.getName());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
        assertNotNull(user.getScopes());
        assertEquals(request.getScopes().size(), user.getScopes().size());
        assertEquals(request.getScopes().get(0), user.getScopes().get(0).getId());
        assertEquals(request.getScopes().get(1), user.getScopes().get(1).getId());
    }

    @Test
    @DisplayName("Should return null when CreateUserRequest is null")
    void toEntityNull() {
        // Given + When
        User user = mapper.toEntity(null);

        // Then
        assertNull(user);
    }

    @Test
    @DisplayName("Should map User entity to UserResponse")
    void toResponse() {
        // Given
        User user = new User();
        user.setName("new user");
        user.setEmail("newuser@java10x.com");
        user.setPassword("password");
        user.setScopes(List.of(Scope.builder().id(34L).name("scope1").build(),
                Scope.builder().id(12L).name("scope2").build()));

        // When
        UserResponse response = mapper.toResponse(user);

        // Then
        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getEmail(), response.getEmail());
        assertNotNull(response.getScopes());
        assertEquals(user.getScopes().size(), response.getScopes().size());
        assertEquals(user.getScopes().get(0).getName(), response.getScopes().get(0));
        assertEquals(user.getScopes().get(1).getName(), response.getScopes().get(1));
    }

    @Test
    @DisplayName("Should return null when User entity is null")
    void toResponseNull() {
        // Given + When
        UserResponse response = mapper.toResponse(null);

        // Then
        assertNull(response);
    }
}