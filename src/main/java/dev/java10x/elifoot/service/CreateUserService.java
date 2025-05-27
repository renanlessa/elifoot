package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateUserRequest;
import dev.java10x.elifoot.controller.response.UserResponse;
import dev.java10x.elifoot.entity.Scope;
import dev.java10x.elifoot.entity.User;
import dev.java10x.elifoot.exception.ResourceAlreadyExistsException;
import dev.java10x.elifoot.mapper.UserMapper;
import dev.java10x.elifoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final FindScopeService findScopeService;

    public UserResponse create(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists, email: " + request.getEmail());
        }

        List<Scope> scopes = request.getScopes().stream()
                .map(findScopeService::findById)
                .toList();

        User newUser = userMapper.toEntity(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setScopes(scopes);
        User user = userRepository.save(newUser);
        return userMapper.toResponse(user);
    }
}
