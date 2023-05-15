package com.maksvell.service.implementations;

import com.maksvell.dto.FleaDto;
import com.maksvell.entity.Flea;
import com.maksvell.mappers.implementations.FleaMapper;
import com.maksvell.repository.FleaRepository;
import com.maksvell.service.interfaces.IFleaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FleaService implements IFleaService {

    private final FleaRepository fleaRepository;
    private final FleaMapper fleaMapper;

    @Autowired
    public FleaService(FleaRepository fleaRepository, FleaMapper fleaMapper) {
        this.fleaRepository = fleaRepository;
        this.fleaMapper = fleaMapper;
    }

    @Override
    public List<FleaDto> getAllFleas() {
        return fleaRepository.findAll()
                .stream()
                .map(fleaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FleaDto getFleaById(Long id) {
        return fleaRepository.findById(id)
                .map(fleaMapper::toDTO)
                .orElse(null);
    }

    @Override
    public FleaDto addFlea(FleaDto fleaDto) {
        Flea flea = fleaMapper.toFleaEntity(fleaDto);
        return fleaMapper.toDTO(fleaRepository.save(flea));
    }

    @Override
    public void deleteFlea(Long id) {
        fleaRepository.deleteById(id);
    }

    @Override
    public FleaDto updateFlea(Long id, FleaDto fleaDto) {
        Flea existingFlea = fleaRepository.findById(id).orElse(null);
        if (existingFlea != null) {
            existingFlea = fleaMapper.toFleaEntity(fleaDto);
            existingFlea.setId(id);
            return fleaMapper.toDTO(fleaRepository.save(existingFlea));
        }
        return null;
    }
}
