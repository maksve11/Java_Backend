package com.maksvell.mappers.implementations;

import com.maksvell.dto.CatDto;
import com.maksvell.entity.Cat;
import com.maksvell.mappers.interfaces.ICatMapper;
import org.springframework.stereotype.Component;

@Component
public class CatMapper implements ICatMapper {
    @Override
    public CatDto toDTO(Cat catEntity) {
        CatDto dto = new CatDto();
        dto.setName(catEntity.getName());
        dto.setBreed(catEntity.getBreed());
        dto.setColor(catEntity.getColor());
        dto.setBirthdate(catEntity.getBirthdate());
        dto.setOwner(catEntity.getOwner());
        dto.setFleas(catEntity.getFleas());
        return dto;
    }

    @Override
    public Cat toCatEntity(CatDto authDto) {
        Cat catEntity = new Cat();
        catEntity.setName(authDto.getName());
        catEntity.setBreed(authDto.getBreed());
        catEntity.setColor(authDto.getColor());
        catEntity.setBirthdate(authDto.getBirthdate());
        catEntity.setOwner(authDto.getOwner());
        catEntity.setFleas(authDto.getFleas());
        return catEntity;
    }
}
