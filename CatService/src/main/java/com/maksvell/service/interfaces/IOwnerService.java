package com.maksvell.service.interfaces;

import com.maksvell.dto.OwnerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOwnerService {
    List<OwnerDto> getAllOwners();
    OwnerDto getOwnerById(Long id);
    OwnerDto addOwner(OwnerDto owner);
    void deleteOwner(Long id);
    OwnerDto updateOwner(Long id, OwnerDto owner);
}
