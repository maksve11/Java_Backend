package com.maksvell.mappers.interfaces;

import com.maksvell.dto.FleaDto;
import com.maksvell.entity.Flea;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IFleaMapper {
    IFleaMapper INSTANCE = Mappers.getMapper(IFleaMapper.class);

    FleaDto toDTO(Flea FleaEntity);

    Flea toFleaEntity(FleaDto authDto);
}
