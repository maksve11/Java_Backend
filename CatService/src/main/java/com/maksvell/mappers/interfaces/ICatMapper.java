package com.maksvell.mappers.interfaces;

import com.maksvell.dto.CatDto;
import com.maksvell.entity.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICatMapper {
    ICatMapper INSTANCE = Mappers.getMapper(ICatMapper.class);

    CatDto toDTO(Cat catEntity);

    Cat toCatEntity(CatDto authDto);
}
