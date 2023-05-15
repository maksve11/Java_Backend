package com.maksvell.service.implementations;

import com.maksvell.dto.requests.AuthDto;
import com.maksvell.entity.User;
import com.maksvell.httpExceptions.BadRequestException;
import com.maksvell.httpExceptions.NotFoundException;
import com.maksvell.repository.UserRepository;
import com.maksvell.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public User registration(AuthDto authDto) {
        User userEntity = userRepository.findByEmail(authDto.getEmail());
        if (userEntity != null) {
            throw new BadRequestException("User is already registered");
        }
        String passwordHash = passwordEncoder.encode(authDto.getPassword());
        return userRepository.save(new User(authDto.getUsername(), authDto.getEmail(), passwordHash));
    }

    @Override
    public User login(AuthDto authDto) {
        User userEntity = userRepository.findByEmail(authDto.getEmail());
        if (userEntity == null) {
            throw new NotFoundException("User with email: " + authDto.getEmail() + " is not registered");
        }
        if (!passwordEquals(userEntity.getPassword(), authDto.getPassword())) {
            throw new BadRequestException("Wrong password or email");
        }
        return userEntity;
    }

    private boolean passwordEquals(String password, String passwordNew) {
        return passwordEncoder.matches(passwordNew, password);
    }
}
