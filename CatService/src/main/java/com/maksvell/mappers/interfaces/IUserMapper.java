package com.maksvell.mappers.interfaces;

import com.maksvell.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.maksvell.entity.User;

@Mapper
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserDto toDTO(User userEntity);

    User toUserEntity(UserDto authDto);
}
