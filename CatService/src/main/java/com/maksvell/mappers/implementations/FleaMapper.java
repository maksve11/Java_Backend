package com.maksvell.mappers.implementations;

import com.maksvell.dto.FleaDto;
import com.maksvell.entity.Flea;
import com.maksvell.mappers.interfaces.IFleaMapper;
import org.springframework.stereotype.Component;

@Component
public class FleaMapper implements IFleaMapper {
    @Override
    public FleaDto toDTO(Flea FleaEntity) {
        FleaDto dto = new FleaDto();
        dto.setName(FleaEntity.getName());
        dto.setCat(FleaEntity.getCat());
        return dto;
    }

    @Override
    public Flea toFleaEntity(FleaDto authDto) {
        Flea FleaEntity = new Flea();
        FleaEntity.setName(authDto.getName());
        FleaEntity.setCat(authDto.getCat());
        return FleaEntity;
    }
}
