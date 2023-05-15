package com.maksvell.service.implementations;

import com.maksvell.entity.enums.Role;
import com.maksvell.httpExceptions.BadRequestException;
import com.maksvell.mappers.implementations.UserMapper;
import com.maksvell.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.maksvell.dto.UserDto;
import com.maksvell.entity.User;
import com.maksvell.repository.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDto userDto) {
        String email = userDto.getEmail();
        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("User already exists");
        }
        User userEntity = userMapper.toUserEntity(userDto);
        userEntity.setActive(true);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.getRoles().add(Role.ROLE_USER);
        log.info("Saving new User with email: {}", email);
        return userRepository.save(userEntity);
    }

    @Override
    public void changeUserRoles(UserDto userDto, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        userDto.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                userDto.getRoles().add(Role.valueOf(key));
            }
        }
        User userEntity = userMapper.toUserEntity(userDto);
        userRepository.save(userEntity);
    }

    @Override
    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        assert user != null;
        userRepository.save(user);
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}