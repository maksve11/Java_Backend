package com.maksvell.mappers.implementations;

import com.maksvell.dto.OwnerDto;
import com.maksvell.entity.Owner;
import com.maksvell.mappers.interfaces.IOwnerMapper;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper implements IOwnerMapper {
    @Override
    public OwnerDto toDTO(Owner OwnerEntity) {
        OwnerDto dto = new OwnerDto();
        dto.setName(OwnerEntity.getName());
        dto.setBirthdate(OwnerEntity.getBirthdate());
        dto.setCats(OwnerEntity.getCats());
        return dto;
    }

    @Override
    public Owner toOwnerEntity(OwnerDto authDto) {
        Owner ownerEntity = new Owner();
        ownerEntity.setName(authDto.getName());
        ownerEntity.setCats(authDto.getCats());
        ownerEntity.setBirthdate(authDto.getBirthdate());
        return ownerEntity;
    }
}
