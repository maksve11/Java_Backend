package com.maksvell.service.implementations;

import com.maksvell.dto.OwnerDto;
import com.maksvell.entity.Owner;
import com.maksvell.mappers.implementations.OwnerMapper;
import com.maksvell.repository.OwnerRepository;
import com.maksvell.service.interfaces.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService implements IOwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerDto getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .map(ownerMapper::toDTO)
                .orElse(null);
    }

    @Override
    public OwnerDto addOwner(OwnerDto ownerDto) {
        Owner entity = ownerMapper.toOwnerEntity(ownerDto);
        entity = ownerRepository.save(entity);
        return ownerMapper.toDTO(entity);
    }

    @Override
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public OwnerDto updateOwner(Long id, OwnerDto ownerDto) {
        Owner existingOwner = ownerRepository.findById(id).orElse(null);
        if (existingOwner != null) {
            existingOwner = ownerMapper.toOwnerEntity(ownerDto);
            existingOwner.setId(id);
            return ownerMapper.toDTO(ownerRepository.save(existingOwner));
        }
        return null;
    }
}
