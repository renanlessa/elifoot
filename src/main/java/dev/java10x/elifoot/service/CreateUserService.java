package dev.java10x.elifoot.service;

import dev.java10x.elifoot.controller.request.CreateUserRequest;
import dev.java10x.elifoot.controller.response.UserResponse;
import dev.java10x.elifoot.entity.Role;
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

    private final FindRoleService findRoleService;

    public UserResponse create(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists, email: " + request.getEmail());
        }

        List<Role> roles = request.getRoles().stream()
                .map(findRoleService::findById)
                .toList();

        User newUser = userMapper.toEntity(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(roles);
        User user = userRepository.save(userMapper.toEntity(request));
        return userMapper.toResponse(user);
    }
}
