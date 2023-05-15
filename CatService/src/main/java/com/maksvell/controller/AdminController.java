package com.maksvell.controller;

import com.maksvell.dto.UserDto;
import com.maksvell.entity.User;
import com.maksvell.httpExceptions.NotFoundException;
import com.maksvell.mappers.implementations.UserMapper;
import com.maksvell.service.implementations.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.maksvell.entity.enums.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Tag(name = "Administration")
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<UserDto> users = userService.findAllUsers();
        List<User> userDtos = users.stream()
                .map(userMapper.INSTANCE::toUserEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("/users/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setActive(false);
        userService.createUser(userMapper.toDTO(user));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{id}/roles")
    public ResponseEntity<?> assignRoles(@PathVariable("id") Long id, @RequestBody List<String> roles) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Set<Role> userRoles = new HashSet<>();
        for (String role : roles) {
            userRoles.add(Role.valueOf(role));
        }
        user.setRoles(userRoles);
        userService.createUser(userMapper.toDTO(user));
        return ResponseEntity.ok().build();
    }
}
