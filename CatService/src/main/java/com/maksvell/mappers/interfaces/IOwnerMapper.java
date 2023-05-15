package com.maksvell.mappers.interfaces;

import com.maksvell.dto.OwnerDto;
import com.maksvell.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IOwnerMapper {
    IOwnerMapper INSTANCE = Mappers.getMapper(IOwnerMapper.class);

    OwnerDto toDTO(Owner OwnerEntity);

    Owner toOwnerEntity(OwnerDto authDto);
}
