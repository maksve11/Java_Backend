package com.maksvell.service.interfaces;

import com.maksvell.dto.FleaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFleaService {
    List<FleaDto> getAllFleas();
    FleaDto getFleaById(Long id);
    FleaDto addFlea(FleaDto flea);
    void deleteFlea(Long id);
    FleaDto updateFlea(Long id, FleaDto flea);
}
