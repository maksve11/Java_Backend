package com.maksvell.service.implementations;

import com.maksvell.dto.CatDto;
import com.maksvell.entity.Cat;
import com.maksvell.mappers.implementations.CatMapper;
import com.maksvell.repository.CatRepository;
import com.maksvell.service.interfaces.ICatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService implements ICatService {

    private final CatRepository catRepository;
    private final CatMapper catMapper;

    @Autowired
    public CatService(CatRepository catRepository, CatMapper catMapper) {
        this.catRepository = catRepository;
        this.catMapper = catMapper;
    }

    @Override
    public List<CatDto> getAllCats() {
        return catRepository.findAll()
                .stream()
                .map(catMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CatDto getCatById(Long id) {
        return catRepository.findById(id)
                .map(catMapper::toDTO)
                .orElse(null);
    }

    @Override
    public CatDto addCat(CatDto catDto) {
        Cat cat = catMapper.toCatEntity(catDto);
        return catMapper.toDTO(catRepository.save(cat));
    }

    @Override
    public void deleteCat(Long id) {
        catRepository.deleteById(id);
    }

    @Override
    public CatDto updateCat(Long id, CatDto catDto) {
        Cat existingCat = catRepository.findById(id).orElse(null);
        if (existingCat != null) {
            existingCat = catMapper.toCatEntity(catDto);
            existingCat.setId(id);
            return catMapper.toDTO(catRepository.save(existingCat));
        }
        return null;
    }
}
