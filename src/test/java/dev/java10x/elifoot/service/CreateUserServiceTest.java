package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateUserRequest;
import dev.java10x.elifoot.entity.Scope;
import dev.java10x.elifoot.entity.User;
import dev.java10x.elifoot.exception.ResourceAlreadyExistsException;
import dev.java10x.elifoot.mapper.UserMapper;
import dev.java10x.elifoot.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @InjectMocks
    CreateUserService createUserService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    FindScopeService findScopeService;
    @Captor
    ArgumentCaptor<User> userCaptor;


    @Test
    @DisplayName("Should throw exception when email already exists")
    void shoulThrowExceptionWhenEmailAlreadyExists() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder().email("email@email.com.br").build();

        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            createUserService.create(request);
        });

        assertEquals("Email already exists, email: " + request.getEmail(), exception.getMessage());
    }

    @Test
    @DisplayName("Should create new user")
    void shouldCreateNewUser() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("John Doe")
                .email("email@email.com.br")
                .password("password")
                .scopes(List.of(1L))
                .build();

        User newUer = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Mockito.when(findScopeService.findById(1L)).thenReturn(Scope.builder().id(1L).build());
        Mockito.when(userMapper.toEntity(request)).thenReturn(newUer);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");


        // When
        createUserService.create(request);

        // Then
        Mockito.verify(findScopeService).findById(1L);
        Mockito.verify(userMapper).toEntity(request);
        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verify(userRepository).save(Mockito.any());
        Mockito.verify(userMapper).toResponse(Mockito.any());

        Mockito.verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        assertNotNull(savedUser.getScopes());
    }

}