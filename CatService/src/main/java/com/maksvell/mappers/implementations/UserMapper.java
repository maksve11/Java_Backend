package com.maksvell.mappers.implementations;

import com.maksvell.dto.UserDto;
import com.maksvell.entity.User;
import com.maksvell.mappers.interfaces.IUserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IUserMapper {
    @Override
    public UserDto toDTO(User userEntity) {
        UserDto dto = new UserDto();
        dto.setUsername(userEntity.getUsername());
        dto.setPassword(userEntity.getPassword());
        dto.setEmail(userEntity.getEmail());
        dto.setOwner(userEntity.getOwner());
        dto.setActive(true);
        dto.setRoles(userEntity.getRoles());
        return dto;
    }

    @Override
    public User toUserEntity(UserDto authDto) {
        User user = new User();
        user.setUsername(authDto.getUsername());
        user.setPassword(authDto.getPassword());
        user.setEmail(authDto.getEmail());
        user.setActive(true);
        user.setOwner(authDto.getOwner());
        user.setRoles(authDto.getRoles());
        return user;
    }
}
