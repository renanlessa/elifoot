package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateUserRequest;
import dev.java10x.elifoot.exception.ResourceAlreadyExistsException;
import dev.java10x.elifoot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @InjectMocks
    CreateUserService createUserService;
    @Mock
    UserRepository userRepository;

    @Test
    void shoulThrowExceptionWhenEmailAlreadyExists() {
        // Given
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("email@email.com.br");

        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            createUserService.create(request);
        });

        assertEquals("Email already exists, email: " + request.getEmail(), exception.getMessage());
    }

    // desenvolver demais cenários de teste
}